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

    static Map<String, Boolean> historyResultMap = null;

    static Map<Integer, Integer> commonCountMap = null;//相同个数生肖的统计情况

    static Map<String, Set<String>> commonCountTicketMap = null;//相同个数生肖的统计情况

    static Map<String, String> animalCountMap;//连续3个的情况

    static boolean printDetail = Boolean.FALSE;//打印预测详情

    static int shiftCount = 4;

    static int hitPosition = 7;//命中的位置1-7
    /*初始化数据*/
    static {
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
        historyTicketDatas = JdbcUtils.getAll();
        animalCountMap = new LinkedHashMap<>();
        for (TicketData ticketData : historyTicketDatas) {
            filterThreeAnimal(ticketData);
        }
        //System.out.println(animalCountMap);
        commonCountTicketMap = new LinkedHashMap<>();
        historyCalculate();

        //zh();
        //ptCount();
        //ptCountDetail("");
        historyTicketDatas = JdbcUtils.getAll();
        Map<String, Integer> choiceAnimal = null;
        int income = 0;
        for (TicketData ticketData : historyTicketDatas) {//计算利润
            choiceAnimal = allChoiceAnimal.get(ticketData.getPeriodNum().toString());//获取选择情况
            //然后计算收益
            if (null != choiceAnimal) {
                income += mathIncome(ticketData, choiceAnimal);
            }
        }
        System.out.println(income);
    }

    private static int mathIncome(TicketData ticketData, Map<String, Integer> choiceAnimal) {
        int totalPay = 0;
        int totalIncome = 0;
        //System.out.println(choiceAnimal);
        for (Map.Entry<String, Integer> entry : choiceAnimal.entrySet()) {
            totalPay += entry.getValue() * 10;
            if (ticketData.getAllAnimalSet().contains(entry.getKey())) {
                totalIncome += 21 * entry.getValue();
            }
        }
        /*if (totalPay >= 40) {
            totalPay = totalPay/2;
            totalIncome = totalIncome/2;
        }*/
        //if ((totalIncome - totalPay) < 0) {
            System.out.println(ticketData.getPeriodNum() + "成本=" + totalPay + ", 命中=" +  totalIncome +  "收益=" + (totalIncome - totalPay));
        //}
        return totalIncome - totalPay;
    }

    static Map<String, Map<String, Integer>> allChoiceAnimal = new HashMap<>();
    /*选择平特的代码*/
    private static void ptCountDetail(String periodNum) {
        MathStack.createHT(false, 2);//初始化组合数据
        Map<String, Set<String>> tMap = MathStack.tMap;
        //historyTicketDatas = JdbcUtils.getAll();
        Map<String, Integer> choiceAnimal = new HashMap<>();
        Map<String, Integer> minNumberMap = minCount(tMap);
        //Map<String, Integer> minNumberMap = new TreeMap<>();;
        int choiceTotal = 0;
        for (Map.Entry<String, Set<String>> entry : tMap.entrySet()) {
            Set<String> tempAniamlSet = entry.getValue();//该生肖为连续出现的情况
            Map<Integer, Integer> countMap = new TreeMap<>();
            Integer count = 0;
            for (TicketData ticketData : historyTicketDatas) {
                if (containsAnimal(ticketData, tempAniamlSet)) {//如果包含
                    if (countMap.get(count) != null) {
                        countMap.put(count, countMap.get(count) + 1);
                    } else {
                        countMap.put(count, 1);
                    }
                    count = 0;
                    break;
                } else {
                    count ++;
                }
            }
            //------
            Set<Integer> keys = countMap.keySet();
            Iterator<Integer> it = keys.iterator();
            while (it.hasNext()) {
                Integer key = it.next();
                if (key != 0) {//不为0的，看是否有匹配最小连续的
                    if (null == minNumberMap.get(entry.getKey()) || minNumberMap.get(entry.getKey()).intValue() != key.intValue()) {
                        it.remove();
                    }
                }
            }
            if (countMap.size() > 0) {
                for (String str : tempAniamlSet) {
                    if (animalCountMap.get(periodNum) != null) {
                        if (!str.equals(animalCountMap.get(periodNum))) {
                            choiceTotal ++;
                            if (choiceAnimal.get(str) != null) {
                                choiceAnimal.put(str, choiceAnimal.get(str) + 1);
                            } else {
                                choiceAnimal.put(str, 1);
                            }
                        }
                    } else {
                        choiceTotal ++;
                        if (choiceAnimal.get(str) != null) {
                            choiceAnimal.put(str, choiceAnimal.get(str) + 1);
                        } else {
                            choiceAnimal.put(str, 1);
                        }
                    }
                }
                //System.out.println(entry.getKey() + "_" + tempAniamlSet + "=" + countMap);
            }
        }
        //System.out.println("选择=" + choiceAnimal.size());
        allChoiceAnimal.put(periodNum, choiceAnimal);
        //System.out.println("选择情况=" + choiceAnimal + ", 总数=" + choiceTotal);
    }

    /*获取各个组合最小连续情况*/
    private static Map<String, Integer> minCount(Map<String, Set<String>> tMap) {
        Map<Integer, Integer> numberCountMap = null;
        Map<String, Integer> minNumberMap = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : tMap.entrySet()) {
            int count = 0;
            numberCountMap = new TreeMap<>();
            for (TicketData ticketData : historyTicketDatas) {
                if (containsAnimal(ticketData, entry.getValue())) {
                    if (null != numberCountMap.get(count)) {
                        numberCountMap.put(count, numberCountMap.get(count) + 1);
                    } else {
                        numberCountMap.put(count, 1);
                    }
                    count = 0;
                } else {
                    count ++;
                }
            }
            if (numberCountMap.size() > 0) {// && !"0".equals(numberCountMap.keySet().toArray()[0].toString())
                if (Integer.valueOf(numberCountMap.keySet().toArray()[0].toString()) <= 2) {//把1的包含进来，算作热数据
                    minNumberMap.put(entry.getKey(), Integer.valueOf(numberCountMap.keySet().toArray()[0].toString()));
                }
                //System.out.println(entry.getKey() + "=" + "" + "=" + numberCountMap);//entry.getValue()//什么是热数据，这里面越多的？这可以是个维度
            }
        }
        return minNumberMap;
    }

    private static void filterThreeAnimal(TicketData ticketData) {
        Map<String, Integer> countMap = new HashMap<>();;//连续3个的情况 = new HashMap<>();
        for (String tempAnimal : ticketData.getAllAnimalList()) {//获取连续三个的
            if (null != countMap.get(tempAnimal)) {
                countMap.put(tempAnimal, countMap.get(tempAnimal) + 1);
            } else {
                countMap.put(tempAnimal, 1);
            }
        }
        //只保留连续三个的
        Set<String> tempAnimalSet = countMap.keySet();
        Iterator<String> animalIt = tempAnimalSet.iterator();
        while (animalIt.hasNext()) {
            String animal = animalIt.next();
            if (countMap.get(animal) >= 3) {
                animalCountMap.put(ticketData.getPeriodNum().toString(), animal);
            }
        }
    }

    private static void ptCount() {
        MathStack.createHT(false, 2);//初始化组合数据
        Map<String, Set<String>> tMap = MathStack.tMap;
        historyTicketDatas = JdbcUtils.getAll();
        //统计概率
        int totalCount = 0;
        int customeCount = 0;
        for (Map.Entry<String, Set<String>> entry : tMap.entrySet()) {
            Set<String> tempAniamlSet = entry.getValue();//该生肖为连续出现的情况
            Map<Integer, Integer> countMap = new TreeMap<>();
            Integer count = 0;
            for (TicketData ticketData : historyTicketDatas) {
                if (containsAnimal(ticketData, tempAniamlSet)) {//如果包含
                    if (countMap.get(count) != null) {
                        countMap.put(count, countMap.get(count) + 1);
                    } else {
                        countMap.put(count, 1);
                    }
                    count = 0;
                } else {
                    count ++;
                }
            }
            //------
            for (Map.Entry<Integer, Integer> entry1 : countMap.entrySet()) {
                totalCount += entry1.getValue();
                if (entry1.getKey() >= 0 && entry1.getKey() <= 3) {
                    customeCount += entry1.getValue();
                }
            }
            System.out.println(entry.getKey() + "_" + tempAniamlSet + "=" + countMap);
        }
        BigDecimal allDecimal = new BigDecimal(customeCount);
        BigDecimal divisor = new BigDecimal(totalCount);
        // 使用四舍五入模式，保留两位小数，注意模式HALF_UP
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        //Double chance = allDecimal.divide(divisor, mc).doubleValue();
        //System.out.println("概率=" + chance);
    }

    private static boolean containsAnimal(TicketData ticketData, Set<String> animalSet) {
        boolean flag = false;
        int count = 0;
        for (String animal : animalSet) {
            if (ticketData.getAllAnimalSet().contains(animal)) {
                /*flag = true;
                break;*/
                count ++;
            }
        }
        //return count == 3 ? true : false;
        return count == animalSet.size() ? true : false;
    }

    /*综合预测平特*/
    private static void zh() {
        commonCountTicketMap = new LinkedHashMap<>();
        for (int i = 0; i < 7; i++) {

            HistoryTicketCalculate.hitPosition = (i+1);
            HistoryTicketCalculate.allOk = 0;
            HistoryTicketCalculate.basicOk = 0;
            historyCalculate();
        }
        System.out.println(commonCountTicketMap);
        Map<Integer, Integer> tempCommonCountMap = new HashMap<>();//相同个数生肖的统计情况
        historyTicketDatas = JdbcUtils.getAll();
        for (TicketData ticketData : historyTicketDatas) {
            Set<String> set = commonCountTicketMap.get(ticketData.getPeriodNum().toString());
            if (null == set || set.size() < 7) {
                continue;
            }
            String str = set.toArray()[0].toString().split("_")[1];
            Set<String> choiceSet = MathStack.tMap.get(str);
            //System.out.println(choiceSet);
            //预测的和全部出的数据相同的关系
            Set<String> commonSet = new LinkedHashSet<>();
            Iterator<String> commonIt = choiceSet.iterator();
            while (commonIt.hasNext()) {
                String animal = commonIt.next();
                if (ticketData.getAllAnimalSet().contains(animal)) {
                    commonSet.add(animal);
                }
            }
            //-------------------------------------------------------------
            if (tempCommonCountMap.get(commonSet.size()) != null) {
                tempCommonCountMap.put(commonSet.size(), tempCommonCountMap.get(commonSet.size()) + 1);
            } else {
                tempCommonCountMap.put(commonSet.size(), 1);
            }
            String temp = "";
            /*for (int i = 0; i < commonSet.size(); i ++) {
                temp += "---";
            }*/
            //+ getCount(set)
            if (commonSet.size() != 1) {
                System.out.println(ticketData.getPeriodNum() + temp + commonSet);//+ ", 备选=" + set + ", 个数=" + set.size()
            }
            temp = "";
        }
        System.out.println(tempCommonCountMap);
    }

    private static String getCount(Set<String> set) {
        Map<String, Integer> tempMap = new HashMap<>();
        Set<String> tempChoiceSet = null;
        for (String str : set) {
            tempChoiceSet = MathStack.tMap.get(str.split("_")[1]);
            for (String animal : tempChoiceSet) {
                if (tempMap.get(animal) != null) {
                    tempMap.put(animal, tempMap.get(animal) + 1);
                } else {
                    tempMap.put(animal, 1);
                }
            }
        }
        return tempMap.toString();
    }

    /*
     * 预测
     * */
    public static void historyCalculate() {
        try {
            //预测开始期数2019059，预测期数2（没有那么多预测时退出）,预测深度就连续出现的阈值6
            long start = System.currentTimeMillis();
            commonCountMap = new HashMap<>();//相同个数生肖的统计情况
            MathStack.createHT(false, shiftCount);//初始化组合数据
            JdbcUtils.repeatAllData();//先修复所有数据正常态
            //createExcelFlag = Boolean.TRUE;
            historyResultMap = new HashMap<>();//预测结果
            multiple(2014001, 150, 6 + shiftCount);
            JdbcUtils.repeatAllData();//先修复所有数据正常态
            System.out.println("耗时=" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            System.out.println("-------------------预测出错了-------------------");
        }
    }

    /*多次预测*/
    private static void multiple(int startPeriodNum, int calculateCount, int calculateDepth) throws Exception {
        TicketData futureTicketData = null;
        int actualCalculateCount = 0;
        for (int i = 0; i < calculateCount; i ++) {
            if (printDetail) {
                System.out.println("                              ||                   ||                              ");
                System.out.println("                              ||  " + startPeriodNum + " ||                              ");
            }
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
            ptCountDetail(startPeriodNum + "");//设置选择的平特数据
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
            if (printDetail) {
                System.out.println("                              ||                   ||                              ");
                System.out.println("                              ||                   ||                              ");
            }
        }
        BigDecimal basicDecimal = new BigDecimal(basicOk);
        BigDecimal allDecimal = new BigDecimal(allOk);
        BigDecimal divisor = new BigDecimal(actualCalculateCount);
        // 使用四舍五入模式，保留两位小数，注意模式HALF_UP
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        Double basicChance = basicDecimal.divide(divisor, mc).doubleValue();
        Double allChance = allDecimal.divide(divisor, mc).doubleValue();
        if (printDetail) {
            System.out.println("====================================");
            System.out.println("====================================");
            System.out.println(String.format("汇总统计情况, 预测深度=%d 预测次数=%d, 基本命中=%d, 全部命中=%d, 基本概率=%s, 全部概率=%s",
                    calculateDepth, actualCalculateCount, basicOk, allOk, basicChance+"", allChance+""));
        }
        System.out.println(commonCountMap);
        System.out.println("位置=" + hitPosition + ", 偏移量=" + shiftCount);
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
        String nowAnimal = "";//当前的出的生肖
        for (int i = 0; i < ticketDatas.size(); i ++) {
            nowAnimal = getHitResult(ticketDatas.get(i));
            // 设置 id
            if (hentry.getValue().contains(nowAnimal)) {
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
                //打印其他连续次数的信息，主要想看看是否偏态和稳定
                /*System.out.println(String.format("组合序号为=%s，出现次数为=%s，对应的组合详情为=正%s 反%s，选择=%s",
                        ticketIdStr, entry.getValue(), MathStack.hMap.get(ticketIdStr), MathStack.tMap.get(ticketIdStr),
                        entry.getValue().indexOf("反") != -1 ? "正" + MathStack.hMap.get(ticketIdStr) : "反" + MathStack.tMap.get(ticketIdStr)));*/
                allSet.addAll(choiceSet);
                if (createExcelFlag) {
                    createExcel(startPeriodNum+"", ticketCount + "_" + ticketIdStr + "预测" + startPeriodNum + "期通过序号_" + entry.getKey(),
                            MathStack.hMap.get(ticketIdStr));
                }

            }
        }
        resultMap.clear();//将这一次的统计情况清除
        if (maxCount > 0) {//设置选择平特的组合数据
            if (null != commonCountTicketMap.get(futureTicketData.getPeriodNum().toString())) {
                Set<String> set = commonCountTicketMap.get(futureTicketData.getPeriodNum().toString());
                set.add(maxCount + "_" + ticketIdStr + "_" + HistoryTicketCalculate.hitPosition);
                commonCountTicketMap.put(futureTicketData.getPeriodNum().toString(), set);
            } else {
                Set<String> set = new TreeSet<>(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {//最大连续不出数_组合序号_位置
                        String[] s1 = o1.split("_");
                        String[] s2 = o2.split("_");
                        if (Integer.valueOf(s1[0]) > Integer.valueOf(s2[0])) {
                            return -1;
                        } else if (Integer.valueOf(s1[0]) < Integer.valueOf(s2[0])) {
                            return 1;
                        } else {
                            if (Integer.valueOf(s1[2]) > Integer.valueOf(s2[2])) {
                                return -1;
                            } else if (Integer.valueOf(s1[2]) < Integer.valueOf(s2[2])) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    }
                });
                set.add(maxCount + "_" + ticketIdStr + "_" + HistoryTicketCalculate.hitPosition);
                commonCountTicketMap.put(futureTicketData.getPeriodNum().toString(), set);
            }
        }

        boolean hort = false;//单双情况，true=双
        if (!StringUtils.isEmpty(maxTicketIdStr)) {
            hort = Integer.valueOf(maxTicketIdStr) % 2 == 0 ? true : false;
            //清除综合选择中的基本选择
            Set<String> otherChoice = new LinkedHashSet<>();//其他选择
            Iterator<String> allIt = allSet.iterator();
            while (allIt.hasNext()) {
                String temp = allIt.next();
                if (!maxSet.contains(temp)) {
                    otherChoice.add(temp);
                }
            }
            if (printDetail) {
                //拼接预测文案
                String choiceContent = String.format("组合序号为=%s, 单双=%s，出现次数为=%s，对应的组合详情为=正%s 反%s，选择=%s, 综合选择=%s, 范围选择=%s",
                        maxTicketIdStr, hort ? "双" : "单", maxCountContent, MathStack.hMap.get(maxTicketIdStr), MathStack.tMap.get(maxTicketIdStr),
                        maxCountContent.indexOf("反") != -1 ? "正" + MathStack.hMap.get(maxTicketIdStr) : "反" + MathStack.tMap.get(maxTicketIdStr),
                        allSet, otherChoice);
                //if (Integer.valueOf(maxTicketIdStr) % 2 == 1) {//组合序号是双数
                System.out.println(choiceContent);
            }
            //}
        } else {
            if (printDetail) {
                System.out.println("无预测的内容");
            }
        }
        //预测的和全部出的数据相同的关系
        Set<String> commonSet = new LinkedHashSet<>();
        Iterator<String> commonIt = maxSet.iterator();
        while (commonIt.hasNext()) {
            String animal = commonIt.next();
            if (futureTicketData.getAllAnimalSet().contains(animal)) {
                commonSet.add(animal);
            }
        }
        if (commonSet.size() != 1) {
            //System.out.println(futureTicketData.getPeriodNum() + "=" + commonSet);
        }
        if (commonCountMap.get(commonSet.size()) != null) {
            commonCountMap.put(commonSet.size(), commonCountMap.get(commonSet.size()) + 1);
        } else {
            commonCountMap.put(commonSet.size(), 1);
        }
        String nowAnimal = getHitResult(futureTicketData);//当前的出的生肖
        //if (Integer.valueOf(maxTicketIdStr) % 2 == 1) {//组合序号是双数
        if (printDetail) {
            System.out.println(futureTicketData.getPeriodNum() + "出=" + nowAnimal +
                    ", 预测命中=" + maxSet.contains(nowAnimal) + ", 全部命中=" + allSet.contains(nowAnimal) +
                    ", 生肖个数=" + futureTicketData.getAllAnimalSet().size() + ", 生肖详情=" + futureTicketData.getAllAnimalSet() +
                    ", 相同的生肖=" + commonSet);
        }
        //}
        historyResultMap.put(futureTicketData.getPeriodNum().toString(), maxSet.contains(nowAnimal));
        if (maxSet.contains(nowAnimal)) {
            basicOk ++;
        }
        if (allSet.contains(nowAnimal)) {
            allOk ++;
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
        String nowAnimal = "";//当前的出的生肖
        for (int i = 0; i < historyTicketDatas.size(); i ++) {
            nowAnimal = getHitResult(historyTicketDatas.get(i));
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
            if (paramSet.contains(nowAnimal)) {
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
            if (lxtemp.equals(nowAnimal)) {
                lxcount ++;
                if (lxcount >= 2) {
                    for (int j = 2; j <= lxcount; j ++) {
                        sheet.getRow(row - j).getCell(5).setCellValue("连续" + nowAnimal + lxcount);
                    }
                }
                cell = excelRow.createCell(5);
                cell.setCellValue("连续" + nowAnimal + lxcount);
            } else {
                lxtemp = nowAnimal;
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
