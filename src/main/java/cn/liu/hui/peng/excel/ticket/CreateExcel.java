package cn.liu.hui.peng.excel.ticket;

import cn.liu.hui.peng.excel.TicketData;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * @description: 导出excel
 * @author: liuhp534
 * @create: 2019-05-29 20:17
 */
public class CreateExcel {

    /*正面*/
    private static final Set<String> hSet;

    /*反面面*/
    private static final Set<String> tSet;

    /*保存开始最大次数的，不是整个数据的*/
    static Map<String, String> resultMap;

    /*历史数据*/
    static List<TicketData> ticketDatas = null;

    /*初始化数据*/
    static {
        /*hSet = new HashSet<>(Arrays.asList("鼠", "牛", "虎", "兔", "龙", "蛇"));
        tSet = new HashSet<>(Arrays.asList("马", "羊", "猴", "鸡", "狗", "猪"));*/
        hSet = new HashSet<>(Arrays.asList("鼠", "羊", "虎", "狗", "龙", "猪"));
        tSet = new HashSet<>(Arrays.asList("马", "牛", "猴", "鸡", "兔", "蛇"));
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
        //createAllRepeatResult();
        //预测开始期数2019059，预测期数2（没有那么多预测时退出）,预测深度就连续出现的阈值6
        long start = System.currentTimeMillis();
        multiple(2018148, 3, 6);
        System.out.println("耗时=" + (System.currentTimeMillis() - start));
    }

    /*计算每种组合的最大出现数
    * 能够更具指定时间内的数据，将所有组合出现的次数统计一遍，并生产excel
    * 还需要打印开始哪期的
    * */
    static Map<String, String> printTreeMap;
    private static void createAllRepeatResult() throws Exception {
        MathStack.createHT(Boolean.FALSE);

        Map<String, Set<String>> hMap = MathStack.hMap;
        Map<String, Set<String>> tMap = MathStack.tMap;
        ticketDatas = JdbcUtils.getAll();

        int threshold = 8;
        printTreeMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String[] ss1 = s1.split("_");
                String[] ss2 = s2.split("_");
                if (Integer.valueOf(ss1[0].substring(0, 4)).intValue() > Integer.valueOf(ss2[0].substring(0, 4)).intValue()) {//比较年份
                    return -1;
                } else if (Integer.valueOf(ss1[0].substring(0, 4)).intValue() < Integer.valueOf(ss2[0].substring(0, 4)).intValue()) {
                    return 1;
                } else {//如果相等，则比较前面那个数据，比较数量
                    if (Integer.valueOf(ss1[1]).intValue() > Integer.valueOf(ss2[1]).intValue()) {
                        return -1;
                    } else if (Integer.valueOf(ss1[1]).intValue() < Integer.valueOf(ss2[1]).intValue()) {
                        return 1;
                    } else {//相等的话就表示同一个数据，并且会更新数据，这里很重要啊
                        if (Integer.valueOf(ss1[2]).intValue() > Integer.valueOf(ss2[2]).intValue()) {
                            return -1;
                        } else if (Integer.valueOf(ss1[2]).intValue() < Integer.valueOf(ss2[2]).intValue()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        });//配置打印的结构
        for (Map.Entry<String, Set<String>> hentry : hMap.entrySet()) {
            configAllRepeat(ticketDatas, hentry, threshold);
        }

        String ticketIdStr = "";
        int ticketCount = 0;
        String peroidNumStr = "";
        Map<String, Integer> commonMap = new HashMap<>();
        for (Map.Entry<String, String> entry : printTreeMap.entrySet()) {
            peroidNumStr = entry.getKey().split("_")[0];
            ticketIdStr = entry.getKey().split("_")[2];
            ticketCount = Integer.valueOf(entry.getKey().split("_")[1]);
            if (ticketCount >= threshold) {
                //获取同一个组合，同样数量的情况
                if (null != commonMap.get(entry.getValue())) {
                    Integer i1 = commonMap.get(entry.getValue());
                    commonMap.put(entry.getValue(), i1 + 1);
                } else {
                    commonMap.put(entry.getValue(), 1);
                }
                //System.out.println(entry.getKey());
                //createExcel("2018数据统计连续最大值大于8", ticketCount + "_" + ticketIdStr + "预测2018数据统计连续最大值大于8期通过序号" + peroidNumStr, MathStack.hMap.get(ticketIdStr));
            }
        }
        for (Map.Entry<String, Integer> entry : commonMap.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + "_" + entry.getValue());
            }
        }
    }
    /*计算每种组合出现连续中的次数，当达到threshold阈值的时候print*/
    private static void configAllRepeat(List<TicketData> ticketDatas, Map.Entry<String, Set<String>> hentry, int threshold) {
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
                } else if ("反".equals(resulttemp)) {//处于反的统计中，到这里表示断开了
                    //if (resultcount >= threshold) {
                        //System.out.println(ticketDatas.get(i).getPeriodNum() + "-" + hentry.getKey()+"_"+resultcount);
                        printTreeMap.put(ticketDatas.get(i-1).getPeriodNum() + "_" + resultcount +"_"+hentry.getKey(), hentry.getKey() +"_"+resultcount);
                    //}
                    //resultMap.put(hentry.getKey()+"_"+resultcount, "反" + resultcount);//tree结构会覆盖，所以不能使用
                    resultcount = 1;
                    resulttemp = "正";
                }
            } else {
                if ("反".equals(resulttemp)) {
                    resultcount ++;
                } else if ("".equals(resulttemp)) {
                    resultcount = 1;
                    resulttemp = "反";
                } else if ("正".equals(resulttemp)) {//处于正的统计中，到这里表示断开了
                    //if (resultcount >= threshold) {
                        //System.out.println(ticketDatas.get(i).getPeriodNum() + "-" + hentry.getKey()+"_"+resultcount);
                        printTreeMap.put(ticketDatas.get(i-1).getPeriodNum()   + "_" + resultcount +"_"+hentry.getKey(), hentry.getKey() +"_"+resultcount);
                    //}
                    //resultMap.put(hentry.getKey()+"_"+resultcount, "正" + resultcount);//tree结构会覆盖，所以不能使用
                    resultcount = 1;
                    resulttemp = "反";;
                }
            }
        }
    }

    /*多次预测*/
    static int basicOk = 0;
    static int allOk = 0;
    private static void multiple(int startPeriodNum, int calculateCount, int calculateDepth) throws Exception {
        MathStack.createHT(false);//初始化组合数据
        TicketData futureTicketData = null;
        int actualCalculateCount = 0;
        for (int i = 0; i < calculateCount; i ++) {
            //比如预测2019059，那么包含这期的之后的都是deleted=1，首先执行update语句
            JdbcUtils.updateByPeriodNumToDeleted(startPeriodNum);
            //获取预测的数据
            ticketDatas = JdbcUtils.getAllForCalculate();
            createHTResult(ticketDatas);//获取resultMap
            futureTicketData = JdbcUtils.getDeletedForCalculate();
            if (null == futureTicketData) {
                System.out.println("预测完成，预测次数=" + (i + 1));
                break;
            }
            //预测
            calculate(startPeriodNum, futureTicketData, calculateDepth);
            actualCalculateCount ++;
            //预测完成之后需要将被预测的恢复，来预测下面的数据
            JdbcUtils.updateByPeriodNum(futureTicketData.getPeriodNum());
            //调到下一个预测
            futureTicketData = JdbcUtils.getDeletedForCalculate();
            if (null == futureTicketData) {
                System.out.println("预测完成，预测次数=" + (i + 1));
                break;
            }
            startPeriodNum = futureTicketData.getPeriodNum();
        }
        BigDecimal basicDecimal = new BigDecimal(basicOk);
        BigDecimal allDecimal = new BigDecimal(allOk);
        BigDecimal divisor = new BigDecimal(actualCalculateCount);
        // 使用四舍五入模式，保留两位小数，注意模式HALF_UP
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        Double basicChance = basicDecimal.divide(divisor, mc).doubleValue();
        Double allChance = allDecimal.divide(divisor, mc).doubleValue();
        System.out.println(String.format("汇总统计情况, 预测深度=%d 预测次数=%d, 基本命中=%d, 全部命中=%d, 基本概率=%s, 全部概率=%s",
                calculateDepth, actualCalculateCount, basicOk, allOk, basicChance+"", allChance+""));
    }

    /*计算下次出的数据*/
    private static void calculate(int startPeriodNum, TicketData futureTicketData, int calculateDepth) throws Exception {
        String ticketIdStr = "";
        int ticketCount = 0;
        Set<String> choiceSet = null;
        Set<String> allSet = new HashSet<>();
        int maxCount = 0;
        Set<String> maxSet = new HashSet<>();
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            ticketIdStr = entry.getKey().split("_")[0];
            ticketCount = Integer.valueOf(entry.getKey().split("_")[1]);
            if (ticketCount >= calculateDepth) {
                choiceSet = entry.getValue().indexOf("反") != -1 ? MathStack.hMap.get(ticketIdStr) : MathStack.tMap.get(ticketIdStr);
                if ((maxCount == 0) || (maxCount > 0 && maxCount < ticketCount)) {
                    maxCount = ticketCount;
                    maxSet = choiceSet;
                }
                allSet.addAll(choiceSet);
                System.out.println(String.format("组合序号为=%s，出现次数为=%s，对应的组合详情为=正%s 反%s，选择=%s",
                        ticketIdStr, entry.getValue(), MathStack.hMap.get(ticketIdStr), MathStack.tMap.get(ticketIdStr),
                        entry.getValue().indexOf("反") != -1 ? "正" + MathStack.hMap.get(ticketIdStr) : "反" + MathStack.tMap.get(ticketIdStr)));
                createExcel(startPeriodNum+"", ticketCount + "_" + ticketIdStr + "预测" + startPeriodNum + "期通过序号_" + entry.getKey(), MathStack.hMap.get(ticketIdStr));
            }
        }
        System.out.println(allSet);
        //System.out.println("========命中情况=========");
        System.out.println(futureTicketData.getPeriodNum() + "出=" + futureTicketData.getSpecial() +
                ", 预测命中=" + maxSet.contains(futureTicketData.getSpecial()) + ", 全部命中=" + allSet.contains(futureTicketData.getSpecial()));
        if (maxSet.contains(futureTicketData.getSpecial())) {
            basicOk ++;
        }
        if (allSet.contains(futureTicketData.getSpecial())) {
            allOk ++;
        }
        //System.out.println("========命中情况=========");
        resultMap.clear();
    }

    /*计算每种组合的开始最大出现数*/
    private static void createHTResult(List<TicketData> dynamicTicketDatas) {
        Map<String, Set<String>> hMap = MathStack.hMap;
        Map<String, Set<String>> tMap = MathStack.tMap;

        for (Map.Entry<String, Set<String>> hentry : hMap.entrySet()) {
            configResult(dynamicTicketDatas, hentry);
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
