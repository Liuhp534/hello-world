package cn.liu.hui.peng.excel.ticket;

import cn.liu.hui.peng.excel.TicketData;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * @description: 预测新的一期
 * @author: liuhp534
 * @create: 2019-05-29 20:17
 */
public class FutureTicketCalculate {

    /*保存开始最大次数的，不是整个数据的*/
    static Map<String, String> resultMap;

    /*历史数据*/
    static List<TicketData> ticketDatas = null;

    /*初始化数据*/
    static {
        MathStack.createHT(false);//初始化组合数据
        Comparator<String> negativeComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {//s1 > s2 返回1是升序，s1 > s2 返回-1是降序
                String[] ss1 = s1.split("_");
                String[] ss2 = s2.split("_");
                if (Integer.valueOf(ss1[1]).intValue() > Integer.valueOf(ss2[1]).intValue()) {
                    return -1;
                } else if (Integer.valueOf(ss1[1]).intValue() < Integer.valueOf(ss2[1]).intValue()) {
                    return 1;
                } else {//如果相等，则比较前面那个数据
                    if (Integer.valueOf(ss1[0]).intValue() > Integer.valueOf(ss2[0]).intValue()) {
                        return -1;
                    } else if (Integer.valueOf(ss1[0]).intValue() < Integer.valueOf(ss2[0]).intValue()) {
                        return 1;
                    } else {//相等的话就表示同一个数据，并且会更新数据，这里很重要啊
                        return 0;
                    }
                }
            }
        };
        resultMap = new TreeMap<>(negativeComparator);
        ticketDatas = JdbcUtils.getAll();
    }

    public static void main(String[] args) throws Exception {
        calculate(5);
    }


    /*计算下次出的数据*/
    private static void calculate(int calculateDepth) throws Exception {
        JdbcUtils.repeatAllData();//先修复所有数据正常态
        int startPeriodNum = ticketDatas.get(0).getPeriodNum() + 1;//获取最后一期，加上1，如果扩年需要特殊处理
        System.out.println("预测期数=" + startPeriodNum);
        String ticketIdStr = "";
        int ticketCount = 0;
        Set<String> choiceSet = null;
        Set<String> allSet = new HashSet<>();
        int maxCount = 0;
        Set<String> maxSet = new HashSet<>();
        calculateHTCount();//获取resultMap，这里是开头连续，不是全部连续，所以treeMap可以，全部的会出现覆盖情况慎用。
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            ticketIdStr = entry.getKey().split("_")[0];
            ticketCount = Integer.valueOf(entry.getKey().split("_")[1]);
            if (ticketCount >= calculateDepth) {
                choiceSet = entry.getValue().indexOf("反") != -1 ? MathStack.hMap.get(ticketIdStr) : MathStack.tMap.get(ticketIdStr);
                if (maxCount == 0) {
                    maxSet = choiceSet;
                    maxCount = ticketCount;
                }
                System.out.println(String.format("组合序号为=%s，出现次数为=%s，对应的组合详情为=正%s 反%s，选择=%s",
                        ticketIdStr, entry.getValue(), MathStack.hMap.get(ticketIdStr), MathStack.tMap.get(ticketIdStr),
                        entry.getValue().indexOf("反") != -1 ? "正" + MathStack.hMap.get(ticketIdStr) : "反" + MathStack.tMap.get(ticketIdStr)));
                allSet.addAll(choiceSet);
                createExcel(startPeriodNum+"", ticketCount + "_" + ticketIdStr + "预测" + startPeriodNum + "期通过序号_" + entry.getKey(), MathStack.hMap.get(ticketIdStr));
            }
        }
        //清除综合选择中的基本选择
        Set<String> otherChoice = new LinkedHashSet<>();//其他选择
        Iterator<String> allIt = allSet.iterator();
        while (allIt.hasNext()) {
            String temp = allIt.next();
            if (!maxSet.contains(temp)) {
                otherChoice.add(temp);
            }
        }
        System.out.println("综合选择=" + allSet + "，其他选择=" + otherChoice);
    }

    /*计算每种组合的开始最大出现数*/
    private static void calculateHTCount() {
        Map<String, Set<String>> hMap = MathStack.hMap;
        Map<String, Set<String>> tMap = MathStack.tMap;

        for (Map.Entry<String, Set<String>> hentry : hMap.entrySet()) {
            configResult(ticketDatas, hentry);
        }
    }

    /*计算每种组合出现连续中的次数*/
    private static void configResult(List<TicketData> ticketDatas, Map.Entry<String, Set<String>> hentry) {
        String resulttemp = "";
        int resultcount = 1;
        for (int i = 0; i < ticketDatas.size(); i ++) {
            // 设置 id
            if (hentry.getValue().contains(ticketDatas.get(i).getSpecial())) {
                if ("正".equals(resulttemp)) {
                    resultcount ++;
                } else if ("".equals(resulttemp)) {
                    resultcount = 1;
                    resulttemp = "正";
                } else if ("反".equals(resulttemp)) {//处于正的统计中，到这里表示断开了
                    resultMap.put(hentry.getKey()+"_"+resultcount, "反" + resultcount);
                    break;
                }
            } else {
                if ("反".equals(resulttemp)) {
                    resultcount ++;
                } else if ("".equals(resulttemp)) {
                    resultcount = 1;
                    resulttemp = "反";
                } else if ("正".equals(resulttemp)) {//处于正的统计中，到这里表示断开了
                    resultMap.put(hentry.getKey()+"_"+resultcount, "正" + resultcount);
                    break;
                }
            }
        }
    }

    /*输出统计excel*/
    private static void createExcel(String num, String excelName, Set<String> paramSet) throws Exception {
        String[] excelHeaders = {"id", "期数", "结果合并", "结果", "次数", "连续"};
        //创建Excel对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表单
        HSSFSheet sheet = workbook.createSheet("结果");
        //创建HSSFRow对象 （行）
        HSSFRow excelRow = sheet.createRow(0);
        //创建HSSFCell对象  （单元格）
        HSSFCell cell = null;
        for (int i = 0; i < excelHeaders.length; i++) {
            cell = excelRow.createCell(i);
            cell.setCellValue(excelHeaders[i]);
        }
        int row = 1;
        String lxtemp = "";
        int lxcount = 0;
        String resulttemp = "";
        int resultcount = 1;
       for (int i = 0; i < ticketDatas.size(); i ++) {
           excelRow = sheet.createRow(row);
           row++;
           // 设置 id
           cell = excelRow.createCell(0);
           cell.setCellValue(ticketDatas.get(i).getId());
           // 设置 id
           cell = excelRow.createCell(1);
           cell.setCellValue(ticketDatas.get(i).getPeriodNum());
           // 设置 id
           cell = excelRow.createCell(2);
           if (paramSet.contains(ticketDatas.get(i).getSpecial())) {
               if ("正".equals(resulttemp)) {
                   resultcount ++;
                   if (resultcount >= 2) {
                       for (int j = 2; j <= resultcount; j ++) {
                           sheet.getRow(row - j).getCell(2).setCellValue("正" + resultcount);
                           sheet.getRow(row - j).getCell(3).setCellValue("正");
                           sheet.getRow(row - j).getCell(4).setCellValue(resultcount+"");
                       }
                   }
                   cell.setCellValue("正" + resultcount);
                   cell = excelRow.createCell(3);
                   cell.setCellValue("正");
                   cell = excelRow.createCell(4);
                   cell.setCellValue(resultcount+"");
               } else {
                   resultcount = 1;
                   cell.setCellValue("正" + resultcount);
                   cell = excelRow.createCell(3);
                   cell.setCellValue("正");
                   cell = excelRow.createCell(4);
                   cell.setCellValue(resultcount+"");
                   resulttemp = "正";
               }
           } else {
               if ("反".equals(resulttemp)) {
                   resultcount ++;
                   if (resultcount >= 2) {
                       for (int j = 2; j <= resultcount; j ++) {
                           sheet.getRow(row - j).getCell(2).setCellValue("反" + resultcount);
                           sheet.getRow(row - j).getCell(3).setCellValue("反");
                           sheet.getRow(row - j).getCell(4).setCellValue(resultcount+"");
                       }
                   }
                   cell.setCellValue("反" + resultcount);
                   cell = excelRow.createCell(3);
                   cell.setCellValue("反");
                   cell = excelRow.createCell(4);
                   cell.setCellValue(resultcount+"");
               } else {
                   resultcount = 1;
                   cell.setCellValue("反" + resultcount);
                   cell = excelRow.createCell(3);
                   cell.setCellValue("反");
                   cell = excelRow.createCell(4);
                   cell.setCellValue(resultcount+"");
                   resulttemp = "反";
               }
           }
           if (lxtemp.equals(ticketDatas.get(i).getSpecial())) {
               lxcount ++;
               cell = excelRow.createCell(5);
               cell.setCellValue("连续" + ticketDatas.get(i).getSpecial() + lxcount);
           } else {
               lxtemp = ticketDatas.get(i).getSpecial();
               lxcount = 0;
           }
       }
        File file = new File("C:\\Users\\liuhp\\Desktop\\ticket\\calculate\\" +num );
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("C:\\Users\\liuhp\\Desktop\\ticket\\calculate\\" +num + "\\" + excelName + ".xls");

        workbook.write(new FileOutputStream(file));
    }













}
