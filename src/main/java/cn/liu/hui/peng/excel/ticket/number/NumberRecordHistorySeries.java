package cn.liu.hui.peng.excel.ticket.number;

import cn.liu.hui.peng.excel.TicketData;
import cn.liu.hui.peng.excel.ticket.AnimalAndNumber;
import cn.liu.hui.peng.excel.ticket.JdbcUtils;
import cn.liu.hui.peng.excel.ticket.MathStack;
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
public class NumberRecordHistorySeries {

    static Map<String, String> printTreeMap;//key=期数+连续数+组合序号+序列号 value=组合序号+连续数

    /*历史数据*/
    static List<TicketData> ticketDatas;

    static int allTreeMapCount;//为了防止重叠的

    //key=期数+连续数+组合序号+序列号 value=组合序号+连续数
    static Comparator<String>  keyComparator = new Comparator<String>() {
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

    static Comparator<String> ignoreCountkeyComparator = new Comparator<String>() {
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

    static Map<Integer, Integer> shiftThresholdMap = new HashMap<>();//配置偏移量显示的深度

    static Map<String, Integer> yearMaxPeriod = new HashMap<>();//记录每年的最大值

    static Map<String, Set<String>> increasePeriodMap = null;//记录期数的汇总情况，将范围内的汇合到一起
    static boolean increasePeriodPrintFlag = Boolean.TRUE;//默认执行操作

    static Map<String, Set<String>> increasePeriodCountMap = null;//记录期数的汇总情况，将范围内的汇合到一起，并且按照最大的优先排序
    static boolean increasePeriodCountPrintFlag = Boolean.TRUE;//默认执行操作

    static boolean increasePeriodDetail = Boolean.TRUE;//默认执行操作,是否打印范围汇合详情

    static String printPeriodNum = "";//如果有值，只输出该值

    static int shiftCount = 7;//偏移量//0-11，0表示排除23or24个, 1表示21or22, 6表示11or12, 7表示9or10, 8表示7or8, 9表示5or6

    static int hitPosition = 7;//命中的位置1-7
    /*初始化数据*/
    private static void init() {
        shiftThresholdMap.put(0, 12);
        shiftThresholdMap.put(1, 11);
        shiftThresholdMap.put(2, 10);
        shiftThresholdMap.put(3, 9);
        shiftThresholdMap.put(4, 8);
        shiftThresholdMap.put(5, 7);
        shiftThresholdMap.put(6, 7);
        shiftThresholdMap.put(7, 5);
        shiftThresholdMap.put(8, 4);
        shiftThresholdMap.put(9, 3);
        shiftThresholdMap.put(10, 3);
        shiftThresholdMap.put(11, 3);

        yearMaxPeriod.put("2013", 2013152);
        yearMaxPeriod.put("2014", 2014152);
        yearMaxPeriod.put("2015", 2015152);
        yearMaxPeriod.put("2016", 2016151);
        yearMaxPeriod.put("2017", 2017153);
        yearMaxPeriod.put("2018", 2018149);
        yearMaxPeriod.put("2019", 2019149);
        //String sql = "select * from ticket_data where create_time >= '" + dataYearStart + "'  order by period_num desc ";
        String sql = "select * from ticket_number_data where create_time >= '2018-01-01' and create_time < '2020-07-01'  order by period_num desc ";
        System.out.println(sql);
        ticketDatas = NumberJdbcUtils.getAllBySql(sql);
        NumberMathStack.createHT(Boolean.FALSE, shiftCount);
        printTreeMap = new TreeMap<>(keyComparator);//核心的数据存储map
        //需要重置的
        allTreeMapCount = 0;//为了防止重叠的
        increasePeriodCountMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {//先按照年，然后次数，然后期数14_2019072
                String[] sts1 = o1.split("_");
                String[] sts2 = o2.split("_");
                if (Integer.valueOf(sts1[1].substring(0, 4)) > Integer.valueOf(sts2[1].substring(0, 4))) {
                    return -1;
                } else if (Integer.valueOf(sts1[1].substring(0, 4)) < Integer.valueOf(sts2[1].substring(0, 4))) {
                    return 1;
                } else {
                    if (Integer.valueOf(sts1[0]) > Integer.valueOf(sts2[0])) {
                        return -1;
                    } else if (Integer.valueOf(sts1[0]) < Integer.valueOf(sts2[0])) {
                        return 1;
                    } else {
                        if (Integer.valueOf(sts1[1]) > Integer.valueOf(sts2[1])) {
                            return -1;
                        } else if (Integer.valueOf(sts1[1]) < Integer.valueOf(sts2[1])) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        });//记录期数的汇总情况，将范围内的汇合到一起，并且按照最大的优先排序
        increasePeriodMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (Integer.valueOf(o1) > Integer.valueOf(o2)) {
                    return -1;
                } else if (Integer.valueOf(o1) < Integer.valueOf(o2)) {
                    return 1;
                }
                return 0;
            }
        });//记录期数的汇总情况，将范围内的汇合到一起
    }

    public static void main(String[] args) throws Exception {
        createAllRepeatResult();
    }

    /*计算每种组合的最大出现数
     * 能够更具指定时间内的数据，将所有组合出现的次数统计一遍，并生产excel
     * 还需要打印开始哪期的
     * */
    public static void createAllRepeatResult() throws Exception {
        init();
        createAllRepeatResult(shiftThresholdMap.get(shiftCount));
    }

    /*计算每种组合的最大出现数
     * 能够更具指定时间内的数据，将所有组合出现的次数统计一遍，并生产excel
     * 还需要打印开始哪期的
     * */
    private static void createAllRepeatResult(int threshold) throws Exception {
        //组合数，已经初始化
        Map<String, Set<String>> hMap = NumberMathStack.hMap;
        Map<String, Set<String>> tMap = NumberMathStack.tMap;

        //配置打印的结构
        for (Map.Entry<String, Set<String>> hentry : hMap.entrySet()) {
            configAllRepeat(ticketDatas, hentry, threshold);
        }

        String ticketIdStr = "";
        int ticketCount = 0;
        String peroidNumStr = "";
        Map<String, Integer> commonMap = new LinkedHashMap<>();
        Map<String, Integer> yearMap = new LinkedHashMap<>();
        String excelName;
        for (Map.Entry<String, String> entry : printTreeMap.entrySet()) {
            peroidNumStr = entry.getKey().split("_")[0];//期数
            ticketCount = Integer.valueOf(entry.getKey().split("_")[1]);//连续数
            ticketIdStr = entry.getKey().split("_")[2];//组合数
            if (ticketCount >= threshold) {
                //统计年份+连续数的数量
                if (null != yearMap.get(peroidNumStr.substring(0, 4) + "|" + ticketCount)) {
                    yearMap.put(peroidNumStr.substring(0, 4) + "|" + ticketCount,
                            yearMap.get(peroidNumStr.substring(0, 4) + "|" + ticketCount) + 1);
                } else {
                    yearMap.put(peroidNumStr.substring(0, 4) + "|" + ticketCount, 1);
                }
                //计算起始位置需要，但是跨年的需要特殊处理
                String startPeriod = entry.getKey().split("_")[0];
                String entPeriod = Integer.valueOf(startPeriod) + Integer.valueOf(entry.getKey().split("_")[1]) - 1 + "";
                if (Integer.valueOf(entPeriod) > Integer.valueOf(yearMaxPeriod.get(entPeriod.substring(0, 4)))) {
                    int overYearIncrement = Integer.valueOf(entPeriod) - Integer.valueOf(yearMaxPeriod.get(entPeriod.substring(0, 4)));
                    entPeriod = (Integer.valueOf(entPeriod.substring(0, 4)) + 1) * 1000 + overYearIncrement + "";
                }
                if (increasePeriodPrintFlag) {
                    if (increasePeriodMap.get(entPeriod) != null) {
                        Set<String> set = increasePeriodMap.get(entPeriod);
                        set.add(entry.getKey() + "=" + entry.getValue() + "=" + startPeriod + "-" + entPeriod);
                    } else {
                        Set<String> set = new LinkedHashSet<>();
                        set.add(entry.getKey() + "=" + entry.getValue() + "=" + startPeriod + "-" + entPeriod);
                        increasePeriodMap.put(entPeriod, set);
                    }
                } else {
                    System.out.println(entry.getKey() + "=" + entry.getValue() + "=" + startPeriod + "-" + entPeriod);
                }
            }
        }
        //输出汇总结果
        if (increasePeriodPrintFlag) {
            String result = "";
            Set<String> hValueSet = null;
            int sortNum = 0;//排序情况，前提是按照最大连续排序才有效果
            boolean printPeriodNumBreak = Boolean.FALSE;//如果指定了打印的期数，后续是否需要执行
            if (!"".equals(printPeriodNum)) {
                printPeriodNumBreak = Boolean.TRUE;
            }
            for (Map.Entry<String, Set<String>> entry : increasePeriodMap.entrySet()) {
                if (increasePeriodCountPrintFlag) {
                    String maxDetail = (String) entry.getValue().toArray()[0];
                    increasePeriodCountMap.put(maxDetail.split("_")[1] + "_" + entry.getKey(), entry.getValue());
                } else {
                    String maxDetail = (String) entry.getValue().toArray()[0];
                    System.out.println("----------------------------------------------------" + (maxDetail.split("_")[1].length() == 1 ? "0" : "") +
                            maxDetail.split("_")[1] + "_" +  entry.getKey() + "--------------------------");
                    if (increasePeriodDetail) {
                        result = entry.getValue().toArray()[0].toString();//只输出第一个；
                        hValueSet = hMap.get(result.split("=")[0].split("_")[2]);
                        System.out.println(result + "=" + hValueSet);
                        AnimalAndNumber.numberToAnimal(hValueSet);
                        /*for (String str : entry.getValue()) {
                            System.out.println(str + "=" + hMap.get(str.split("=")[0].split("_")[2]));
                        }*/
                    }
                }
            }
            if (increasePeriodCountPrintFlag) {
                for (Map.Entry<String, Set<String>> entry : increasePeriodCountMap.entrySet()) {
                    if (!printPeriodNumBreak) {
                        System.out.println("----------------------------------------------------" + entry.getKey() + "--------------------------");
                    }
                    if (increasePeriodDetail) {
                        sortNum ++;
                        if (printPeriodNumBreak) {
                            for (String str : entry.getValue()) {
                                if (printPeriodNum.equals(entry.getKey().split("_")[1])) {
                                    System.out.println("----------------------------------------------------" + entry.getKey() + "--------------------------");
                                    System.out.println(str + "=" + hMap.get(str.split("=")[0].split("_")[2]) + ", 历史排=" + sortNum);
                                    hValueSet = hMap.get(str.split("=")[0].split("_")[2]);
                                    AnimalAndNumber.numberToAnimal(hValueSet);
                                    break;
                                }
                            }
                            if (printPeriodNum.equals(entry.getKey().split("_")[1])) {
                                break;
                            }
                        } else {
                            result = entry.getValue().toArray()[0].toString();//只输出第一个；
                            hValueSet = hMap.get(result.split("=")[0].split("_")[2]);
                            System.out.println(result + "=" + hValueSet);
                            AnimalAndNumber.numberToAnimal(hValueSet);
                        }
                    }
                }
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
        System.out.println("数据量量=" + temp + ", 位置=" + hitPosition + ", 偏移量=" + shiftCount);
    }

    /*计算每种组合出现连续中的次数，当达到threshold阈值的时候print*/
    private static void configAllRepeat(List<TicketData> ticketDatas, Map.Entry<String, Set<String>> hentry, int threshold) {
        String resulttemp = "";
        int resultcount = 1;
        for (int i = 0; i < ticketDatas.size(); i ++) {
            // 设置 id
            if (hentry.getValue().contains(getHitResult(ticketDatas.get(i)))) {
                if ("正".equals(resulttemp)) {
                    resultcount ++;
                } else if ("".equals(resulttemp)) {
                    resultcount = 1;
                    resulttemp = "正";
                } else if ("反".equals(resulttemp)) {//处于反的统计中，到这里表示断开了
                    if (resultcount >= threshold) {
                        /*allTreeMapCount ++;
                        printTreeMap.put(ticketDatas.get(i-1).getPeriodNum() + "_" + resultcount +"_"+hentry.getKey() + "_" + allTreeMapCount,
                                "反" + resultcount +"_"+ hentry.getKey());*/
                    }
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
                    if (resultcount >= threshold) {
                        allTreeMapCount ++;
                        printTreeMap.put(ticketDatas.get(i-1).getPeriodNum()   + "_" + resultcount +"_"+hentry.getKey() + "_" + allTreeMapCount,
                                "正" + resultcount +"_"+ hentry.getKey());
                    }
                    resultcount = 1;
                    resulttemp = "反";
                }
            }
        }
    }


    /*根据位置信息获取命中的数据hitPosition*/
    private static String getHitResult(TicketData nowTicketData) {
        switch (hitPosition) {
            case 1:
                return nowTicketData.getPosition1();
            case 2:
                return nowTicketData.getPosition2();
            case 3:
                return nowTicketData.getPosition3();
            case 4:
                return nowTicketData.getPosition4();
            case 5:
                return nowTicketData.getPosition5();
            case 6:
                return nowTicketData.getPosition6();
            case 7:
                return nowTicketData.getSpecial();
            default:
                System.out.println("----------------");
                System.out.println("----------------");
                System.out.println("-------位置错误---------");
                System.out.println("----------------");
                System.out.println("----------------");
                return null;
        }
    }















}
