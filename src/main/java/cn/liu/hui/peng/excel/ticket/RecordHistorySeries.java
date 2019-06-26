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
 * @description: 记录历史数据的连续情况
 * @author: liuhp534
 * @create: 2019-05-29 20:17
 */
public class RecordHistorySeries {

    static Map<String, String> printTreeMap;//key=期数+连续数+组合序号+序列号 value=组合序号+连续数

    /*历史数据*/
    static List<TicketData> ticketDatas;

    static int allTreeMapCount = 0;//为了防止重叠的

    static Comparator<String> keyComparator;

    static Comparator<String> ignoreCountkeyComparator;

    private static boolean createExcelFlag = Boolean.FALSE;//默认不打印excel

    //获取正反相差数量的tree，2019年6月7日16:27:00 为了查看正反的极限偏差
    static Map<String, String> countDifferMap = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            String[] ss1 = s1.split("_");
            String[] ss2 = s2.split("_");
            if (Integer.valueOf(ss1[1]) > Integer.valueOf(ss2[1])) {
                return -1;
            } else if (Integer.valueOf(ss1[1]) < Integer.valueOf(ss2[1])) {
                return 1;
            } else {
                if (Integer.valueOf(ss1[0]) > Integer.valueOf(ss2[0])) {
                    return -1;
                } else if (Integer.valueOf(ss1[0]) < Integer.valueOf(ss2[0])) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    });

    static String dataYearStart = "2018-01-01到2018-07-01";

    static int shiftCount = 1;
    /*初始化数据*/
    static {
        //String sql = "select * from ticket_data where create_time >= '" + dataYearStart + "'  order by period_num desc ";
        String sql = "select * from ticket_data where create_time >= '2018-01-01' and create_time < '2020-07-01'  order by period_num desc ";
        System.out.println(sql);
        ticketDatas = JdbcUtils.getAllBySql(sql);
        MathStack.createHT(Boolean.FALSE, shiftCount);
        //key=期数+连续数+组合序号+序列号 value=组合序号+连续数
        keyComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String[] ss1 = s1.split("_");
                String[] ss2 = s2.split("_");
                if (Integer.valueOf(ss1[0].substring(0, 4)).intValue() > Integer.valueOf(ss2[0].substring(0, 4)).intValue()) {//比较年份
                    return -1;
                } else if (Integer.valueOf(ss1[0].substring(0, 4)).intValue() < Integer.valueOf(ss2[0].substring(0, 4)).intValue()) {
                    return 1;
                } else {
                    if (Integer.valueOf(ss1[1]).intValue() > Integer.valueOf(ss2[1]).intValue()) {//如果相等，则比较前面那个数据，比较数量
                        return -1;
                    } else if (Integer.valueOf(ss1[1]).intValue() < Integer.valueOf(ss2[1]).intValue()) {
                        return 1;
                    } else {
                        if (Integer.valueOf(ss1[0]).intValue() > Integer.valueOf(ss2[0]).intValue()) {//起始位置，比较
                            return -1;
                        } else if (Integer.valueOf(ss1[0]).intValue() < Integer.valueOf(ss2[0]).intValue()) {
                            return 1;
                        } else {
                            if (Integer.valueOf(ss1[2]).intValue() > Integer.valueOf(ss2[2]).intValue()) {//比较组合序号
                                return -1;
                            } else if (Integer.valueOf(ss1[2]).intValue() < Integer.valueOf(ss2[2]).intValue()) {
                                return 1;
                            } else {
                                if (Integer.valueOf(ss1[3]).intValue() > Integer.valueOf(ss2[3]).intValue()) {//比较序列号
                                    return -1;
                                } else if (Integer.valueOf(ss1[3]).intValue() < Integer.valueOf(ss2[3]).intValue()) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                        }
                    }
                }
            }
        };
        ignoreCountkeyComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String[] ss1 = s1.split("_");
                String[] ss2 = s2.split("_");
                if (Integer.valueOf(ss1[0].substring(0, 4)).intValue() > Integer.valueOf(ss2[0].substring(0, 4)).intValue()) {//比较年份
                    return -1;
                } else if (Integer.valueOf(ss1[0].substring(0, 4)).intValue() < Integer.valueOf(ss2[0].substring(0, 4)).intValue()) {
                    return 1;
                } else {
                    if (Integer.valueOf(ss1[0]).intValue() > Integer.valueOf(ss2[0]).intValue()) {//起始位置，比较
                        return -1;
                    } else if (Integer.valueOf(ss1[0]).intValue() < Integer.valueOf(ss2[0]).intValue()) {
                        return 1;
                    } else {
                        if (Integer.valueOf(ss1[2]).intValue() > Integer.valueOf(ss2[2]).intValue()) {//比较组合序号
                            return -1;
                        } else if (Integer.valueOf(ss1[2]).intValue() < Integer.valueOf(ss2[2]).intValue()) {
                            return 1;
                        } else {
                            if (Integer.valueOf(ss1[3]).intValue() > Integer.valueOf(ss2[3]).intValue()) {//比较序列号
                                return -1;
                            } else if (Integer.valueOf(ss1[3]).intValue() < Integer.valueOf(ss2[3]).intValue()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    }
                }
            }
        };
        printTreeMap = new TreeMap<>(keyComparator);
    }

    static int maxThreshold = 100;//最大能打印excel的连续出现期数的最大值
    public static void main(String[] args) throws Exception {
        //createExcelFlag = Boolean.TRUE;//这个可以生产excel
        int threshold = 6 + shiftCount;
        createAllRepeatResult(threshold);
    }

    /*计算每种组合的最大出现数
    * 能够更具指定时间内的数据，将所有组合出现的次数统计一遍，并生产excel
    * 还需要打印开始哪期的
    * */
    private static void createAllRepeatResult(int threshold) throws Exception {
        /*if (threshold <= 6) {
            return;
        }*/
        Map<String, Set<String>> hMap = MathStack.hMap;
        Map<String, Set<String>> tMap = MathStack.tMap;

        //配置打印的结构
        for (Map.Entry<String, Set<String>> hentry : hMap.entrySet()) {
            configAllRepeat(ticketDatas, hentry, threshold);
        }
        //打印相差的量
        for (Map.Entry<String, String> entry : countDifferMap.entrySet()) {
            String ticketIdStr = entry.getKey().split("_")[0];
            int differCount = Integer.valueOf(entry.getKey().split("_")[1]);
            if (differCount >= 13) {
                //System.out.println(entry.getKey() + "=" + entry.getValue());
                createExcel(dataYearStart, differCount + "_相差数量" + "_组合数_" + ticketIdStr, MathStack.hMap.get(ticketIdStr));
            }
        }

        String ticketIdStr = "";
        int ticketCount = 0;
        String peroidNumStr = "";
        Map<String, Integer> commonMap = new LinkedHashMap<>();
        Map<String, Integer> yearMap = new LinkedHashMap<>();
        String excelName;
        for (Map.Entry<String, String> entry : printTreeMap.entrySet()) {
            peroidNumStr = entry.getKey().split("_")[0];//期数
            ticketIdStr = entry.getKey().split("_")[2];//组合数
            ticketCount = Integer.valueOf(entry.getKey().split("_")[1]);//连续数
            if (ticketCount >= threshold && ticketCount < maxThreshold) {
                //获取同一个组合，同样数量的情况
                if (null != commonMap.get(entry.getValue())) {
                    Integer i1 = commonMap.get(entry.getValue());
                    commonMap.put(entry.getValue(), i1 + 1);
                } else {
                    commonMap.put(entry.getValue(), 1);
                }
                //统计年份+连续数的数量
                if (null != yearMap.get(peroidNumStr.substring(0, 4) + "|" + ticketCount)) {
                    yearMap.put(peroidNumStr.substring(0, 4) + "|" + ticketCount,
                            yearMap.get(peroidNumStr.substring(0, 4) + "|" + ticketCount) + 1);
                } else {
                    yearMap.put(peroidNumStr.substring(0, 4) + "|" + ticketCount, 1);
                }
                //System.out.println(entry.getKey());
                String startPeriod = entry.getKey().split("_")[0];
                String entPeriod = Integer.valueOf(startPeriod) + Integer.valueOf(entry.getKey().split("_")[1]) - 1 + "";
               System.out.println(entry.getKey() + "=" + entry.getValue() + "=" + startPeriod + "-" + entPeriod);
                if (createExcelFlag && threshold > 8) {
                    excelName = ticketCount + "_" + ticketIdStr + "预测2018数据统计连续最大值大于8期通过序号" + peroidNumStr + "_" + entry.getKey().split("_")[3];
                    createExcel(dataYearStart + "数据统计连续最大值大于" + threshold, excelName, MathStack.hMap.get(ticketIdStr));
                }
            }
        }
        for (Map.Entry<String, Integer> entry : commonMap.entrySet()) {
            if (entry.getValue() > 1) {
                //System.out.println(entry.getKey() + "_" + entry.getValue());
            }
        }
        int temp = 0;//验证是否数据量匹配上了
        Map<String, Integer> yearTotalMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : yearMap.entrySet()) {
            temp += entry.getValue();
            if (null != yearTotalMap.get(entry.getKey().split("\\|")[0])) {
                yearTotalMap.put(entry.getKey().split("\\|")[0],
                        yearTotalMap.get(entry.getKey().split("\\|")[0]) + entry.getValue());
            } else {
                yearTotalMap.put(entry.getKey().split("\\|")[0], entry.getValue());
            }
        }
        for (Map.Entry<String, Integer> entry : yearMap.entrySet()) {
            /*System.out.println(entry.getKey() + "，次数=" + entry.getValue() + "，概率=" + mathProportion(entry.getValue(),
                    yearTotalMap.get(entry.getKey().split("\\|")[0])));*/
        }
        /*System.out.println(printTreeMap.size());
        System.out.println(allTreeMapCount);
        System.out.println(temp);*/
        System.out.println(temp);
    }

    /*计算比例*/
    private static Double mathProportion(int basicCount, int totalCount) {
        BigDecimal basicDecimal = new BigDecimal(basicCount);
        BigDecimal allDecimal = new BigDecimal(totalCount);
        // 使用四舍五入模式，保留两位小数，注意模式HALF_UP
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        Double basicChance = basicDecimal.divide(allDecimal, mc).doubleValue();
        //System.out.println(basicCount + "_" + totalCount + "_" + basicChance);
        return basicChance;
    }

    /*计算每种组合出现连续中的次数，当达到threshold阈值的时候print*/
    private static void configAllRepeat(List<TicketData> ticketDatas, Map.Entry<String, Set<String>> hentry, int threshold) {
        String resulttemp = "";
        int resultcount = 1;
        int hCount = 0;
        int tCount = 0;
        for (int i = 0; i < ticketDatas.size(); i ++) {
            // 设置 id
            if (hentry.getValue().contains(ticketDatas.get(i).getSpecial())) {
                hCount ++;
                if ("正".equals(resulttemp)) {
                    resultcount ++;
                } else if ("".equals(resulttemp)) {
                    resultcount = 1;
                    resulttemp = "正";
                } else if ("反".equals(resulttemp)) {//处于反的统计中，到这里表示断开了
                    if (resultcount >= threshold) {
                        //System.out.println(ticketDatas.get(i).getPeriodNum() + "-" + hentry.getKey()+"_"+resultcount);
                        allTreeMapCount ++;
                        printTreeMap.put(ticketDatas.get(i-1).getPeriodNum() + "_" + resultcount +"_"+hentry.getKey() + "_" + allTreeMapCount,
                                "反" + resultcount +"_"+ hentry.getKey());
                    }
                    resultcount = 1;
                    resulttemp = "正";
                }
            } else {
                tCount ++;
                if ("反".equals(resulttemp)) {
                    resultcount ++;
                } else if ("".equals(resulttemp)) {
                    resultcount = 1;
                    resulttemp = "反";
                } else if ("正".equals(resulttemp)) {//处于正的统计中，到这里表示断开了
                    if (resultcount >= threshold) {
                        //System.out.println(ticketDatas.get(i).getPeriodNum() + "-" + hentry.getKey()+"_"+resultcount);
                        allTreeMapCount ++;
                        printTreeMap.put(ticketDatas.get(i-1).getPeriodNum()   + "_" + resultcount +"_"+hentry.getKey() + "_" + allTreeMapCount,
                                "正" + resultcount +"_"+ hentry.getKey());
                    }
                    resultcount = 1;
                    resulttemp = "反";
                }
            }
        }
        int tempCount =  (hCount - tCount) > 0 ? hCount - tCount : tCount - hCount;
        countDifferMap.put(hentry.getKey() + "_" + tempCount, "正" + hCount + "_" + "反" + tCount);
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
               if (lxcount >= 2) {
                   for (int j = 2; j <= lxcount; j ++) {
                       sheet.getRow(row - j).getCell(5).setCellValue("连续" + ticketDatas.get(i).getSpecial() + lxcount);
                   }
               }
               cell = excelRow.createCell(5);
               cell.setCellValue("连续" + ticketDatas.get(i).getSpecial() + lxcount);
           } else {
               lxtemp = ticketDatas.get(i).getSpecial();
               lxcount = 1;
               cell = excelRow.createCell(5);
               cell.setCellValue("");
           }
       }
        File file = new File("C:\\Users\\liuhp\\Desktop\\ticket\\连续数据统计\\" +num );
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("C:\\Users\\liuhp\\Desktop\\ticket\\连续数据统计\\" +num + "\\" + excelName + ".xls");

        workbook.write(new FileOutputStream(file));
    }













}
