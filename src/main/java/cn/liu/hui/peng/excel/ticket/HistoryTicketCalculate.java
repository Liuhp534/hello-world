package cn.liu.hui.peng.excel.ticket;

import cn.liu.hui.peng.excel.TicketData;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * @description: 历史模拟
 * @author: liuhp534
 * @create: 2019-05-29 20:17
 */
public class HistoryTicketCalculate {

    /*保存开始最大次数的，不是整个数据的*/
    static Map<String, String> resultMap;

    /*多次预测*/
    static int basicOk = 0;
    static int allOk = 0;

    /*动态的历史数据，预测的时候才能够确定*/
    static List<TicketData> historyTicketDatas;

    private static boolean createExcelFlag = Boolean.FALSE;

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
    }

    public static void main(String[] args) throws Exception {
        //预测开始期数2019059，预测期数2（没有那么多预测时退出）,预测深度就连续出现的阈值6
        long start = System.currentTimeMillis();
        JdbcUtils.repeatAllData();//先修复所有数据正常态
        createExcelFlag = Boolean.TRUE;
        multiple(2019001, 61, 5);
        JdbcUtils.repeatAllData();//先修复所有数据正常态
        System.out.println("耗时=" + (System.currentTimeMillis() - start));
    }

    /*多次预测*/
    private static void multiple(int startPeriodNum, int calculateCount, int calculateDepth) throws Exception {
        TicketData futureTicketData = null;
        int actualCalculateCount = 0;
        for (int i = 0; i < calculateCount; i ++) {
            System.out.println("                              ||                   ||                              ");
            System.out.println("                              ||  " + startPeriodNum + " ||                              ");
            //比如预测2019059，那么包含这期的之后的都是deleted=1，首先执行update语句
            JdbcUtils.updateByPeriodNumToDeleted(startPeriodNum);
            //获取预测的数据
            historyTicketDatas = JdbcUtils.getAllForCalculate();//每次需要获取需要预测的历史数据，即被预测期数，之前的所有数据
            calculateHTCount();//获取resultMap，这里是开头连续，不是全部连续，所以treeMap可以，全部的会出现覆盖情况慎用。
            futureTicketData = JdbcUtils.getDeletedForCalculate();//获取隐藏数据的最小的那条，也就是被预测的数据。
            if (null == futureTicketData) {
                System.out.println("该期数不能预测");
                return;
            }
            //核心功能：预测
            calculate(startPeriodNum, futureTicketData, calculateDepth);
            actualCalculateCount ++;
            //预测完成之后需要将被预测的恢复，来预测下期的数据
            JdbcUtils.updateByPeriodNum(futureTicketData.getPeriodNum());
            //获取下一个预测
            futureTicketData = JdbcUtils.getDeletedForCalculate();
            if (null == futureTicketData) {//如果不存在下期，那么预测结束
                System.out.println("没有更多了，程序结束，预测完成，预测次数=" + (i + 1));
                break;
            }
            startPeriodNum = futureTicketData.getPeriodNum();
            System.out.println("                              ||                   ||                              ");
            System.out.println("                              ||                   ||                              ");
        }
        BigDecimal basicDecimal = new BigDecimal(basicOk);
        BigDecimal allDecimal = new BigDecimal(allOk);
        BigDecimal divisor = new BigDecimal(actualCalculateCount);
        // 使用四舍五入模式，保留两位小数，注意模式HALF_UP
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        Double basicChance = basicDecimal.divide(divisor, mc).doubleValue();
        Double allChance = allDecimal.divide(divisor, mc).doubleValue();
        System.out.println("====================================");
        System.out.println("====================================");
        System.out.println(String.format("汇总统计情况, 预测深度=%d 预测次数=%d, 基本命中=%d, 全部命中=%d, 基本概率=%s, 全部概率=%s",
                calculateDepth, actualCalculateCount, basicOk, allOk, basicChance+"", allChance+""));
    }

    /*计算每种组合的开始最大出现数*/
    private static void calculateHTCount() {
        Map<String, Set<String>> hMap = MathStack.hMap;
        Map<String, Set<String>> tMap = MathStack.tMap;

        for (Map.Entry<String, Set<String>> hentry : hMap.entrySet()) {
            configResult(historyTicketDatas, hentry);
        }
    }

    /*计算每种组合出现连续中的次数,随着历史数据的变动，次数不同，这里是开头连续，不是全部连续*/
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

    /*计算下次出的数据*/
    private static void calculate(int startPeriodNum, TicketData futureTicketData, int calculateDepth) throws Exception {
        String ticketIdStr = "";
        int ticketCount = 0;
        Set<String> choiceSet = null;
        Set<String> allSet = new HashSet<>();
        int maxCount = 0;
        String maxCountContent = "";
        String maxTicketIdStr = "";
        Set<String> maxSet = new LinkedHashSet<>();
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {//这里是开头连续，不是全部连续，所以treeMap可以，全部的会出现覆盖情况慎用。
            //System.out.println(resultMap.size() + "=" + entry.getKey());//理论说一定全是等于462
            ticketIdStr = entry.getKey().split("_")[0];
            ticketCount = Integer.valueOf(entry.getKey().split("_")[1]);
            if (ticketCount >= calculateDepth) {
                choiceSet = entry.getValue().indexOf("反") != -1 ? MathStack.hMap.get(ticketIdStr) : MathStack.tMap.get(ticketIdStr);//当连续出现在反时，需要选择正向
                if ((maxCount == 0) || (maxCount > 0 && maxCount < ticketCount)) {//一般只打印序次数最大的那个，最先出现的
                    maxCount = ticketCount;
                    maxCountContent = entry.getValue();
                    maxSet = choiceSet;
                    maxTicketIdStr = ticketIdStr;
                }
                allSet.addAll(choiceSet);
                if (createExcelFlag) {
                    createExcel(startPeriodNum+"", ticketCount + "_" + ticketIdStr + "预测" + startPeriodNum + "期通过序号_" + entry.getKey(),
                            MathStack.hMap.get(ticketIdStr));
                }

            }
        }
        resultMap.clear();//将这一次的统计情况清除
        if (!StringUtils.isEmpty(maxTicketIdStr)) {
            //清除综合选择中的基本选择
            Set<String> otherChoice = new LinkedHashSet<>();//其他选择
            Iterator<String> allIt = allSet.iterator();
            while (allIt.hasNext()) {
                String temp = allIt.next();
                if (!maxSet.contains(temp)) {
                    otherChoice.add(temp);
                }
            }
            //拼接预测文案
            String choiceContent = String.format("组合序号为=%s，出现次数为=%s，对应的组合详情为=正%s 反%s，选择=%s, 综合选择=%s, 范围选择=%s",
                    maxTicketIdStr, maxCountContent, MathStack.hMap.get(maxTicketIdStr), MathStack.tMap.get(maxTicketIdStr),
                    maxCountContent.indexOf("反") != -1 ? "正" + MathStack.hMap.get(ticketIdStr) : "反" + MathStack.tMap.get(ticketIdStr),
                    allSet, otherChoice);
            System.out.println(choiceContent);
        } else {
            System.out.println("无预测的内容");
        }
        System.out.println(futureTicketData.getPeriodNum() + "出=" + futureTicketData.getSpecial() +
                ", 预测命中=" + maxSet.contains(futureTicketData.getSpecial()) + ", 全部命中=" + allSet.contains(futureTicketData.getSpecial()));
        if (maxSet.contains(futureTicketData.getSpecial())) {
            basicOk ++;
        }
        if (allSet.contains(futureTicketData.getSpecial())) {
            allOk ++;
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
        int lxcount = 1;
        String resulttemp = "";
        int resultcount = 1;
       for (int i = 0; i < historyTicketDatas.size(); i ++) {
           excelRow = sheet.createRow(row);
           row++;
           // 设置 id
           cell = excelRow.createCell(0);
           cell.setCellValue(historyTicketDatas.get(i).getId());
           // 设置 id
           cell = excelRow.createCell(1);
           cell.setCellValue(historyTicketDatas.get(i).getPeriodNum());
           // 设置 id
           cell = excelRow.createCell(2);
           if (paramSet.contains(historyTicketDatas.get(i).getSpecial())) {
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
           if (lxtemp.equals(historyTicketDatas.get(i).getSpecial())) {
               lxcount ++;
               if (lxcount >= 2) {
                   for (int j = 2; j <= lxcount; j ++) {
                       sheet.getRow(row - j).getCell(5).setCellValue("连续" + historyTicketDatas.get(i).getSpecial() + lxcount);
                   }
               }
               cell = excelRow.createCell(5);
               cell.setCellValue("连续" + historyTicketDatas.get(i).getSpecial() + lxcount);
           } else {
               lxtemp = historyTicketDatas.get(i).getSpecial();
               lxcount = 1;
               cell = excelRow.createCell(5);
               cell.setCellValue("");
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
