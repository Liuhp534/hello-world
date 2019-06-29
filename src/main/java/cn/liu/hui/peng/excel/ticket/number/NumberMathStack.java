package cn.liu.hui.peng.excel.ticket.number;

import cn.liu.hui.peng.excel.ticket.EStack;

import java.util.*;

/**
 * @description: 组合的使用
 * @author: hz16092620
 * @create: 2019-05-30 11:53
 */
public class NumberMathStack {

    static int cnt = 0;
    static EStack<Integer> s = new EStack<Integer>();
    public static Map<String, Set<String>> hMap = new LinkedHashMap<>();
    public  static Map<String, Set<String>> tMap = new LinkedHashMap<>();
    static Set<String> allSet =
            new LinkedHashSet<>(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                    "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"));//25个里面分成两堆
    //static Set<String> allSet = new LinkedHashSet<>(Arrays.asList("01", "02", "03", "04"));//4个里面分成均等两堆

    /*
     * number的映射关系
     * */
    static Map<String, Set<String>> numberHTMap = new HashMap<>();

    static {
        numberHTMap.put("01", new HashSet<String>(Arrays.asList("01", "49")));
        numberHTMap.put("02", new HashSet<String>(Arrays.asList("02", "48")));
        numberHTMap.put("03", new HashSet<String>(Arrays.asList("03", "47")));
        numberHTMap.put("04", new HashSet<String>(Arrays.asList("04", "46")));
        numberHTMap.put("05", new HashSet<String>(Arrays.asList("05", "45")));
        numberHTMap.put("06", new HashSet<String>(Arrays.asList("06", "44")));
        numberHTMap.put("07", new HashSet<String>(Arrays.asList("07", "43")));
        numberHTMap.put("08", new HashSet<String>(Arrays.asList("08", "42")));
        numberHTMap.put("09", new HashSet<String>(Arrays.asList("09", "41")));
        numberHTMap.put("10", new HashSet<String>(Arrays.asList("10", "40")));
        numberHTMap.put("11", new HashSet<String>(Arrays.asList("11", "39")));
        numberHTMap.put("12", new HashSet<String>(Arrays.asList("12", "38")));
        numberHTMap.put("13", new HashSet<String>(Arrays.asList("13", "37")));
        numberHTMap.put("14", new HashSet<String>(Arrays.asList("14", "36")));
        numberHTMap.put("15", new HashSet<String>(Arrays.asList("15", "35")));
        numberHTMap.put("16", new HashSet<String>(Arrays.asList("16", "34")));
        numberHTMap.put("17", new HashSet<String>(Arrays.asList("17", "33")));
        numberHTMap.put("18", new HashSet<String>(Arrays.asList("18", "32")));
        numberHTMap.put("19", new HashSet<String>(Arrays.asList("19", "31")));
        numberHTMap.put("20", new HashSet<String>(Arrays.asList("20", "30")));
        numberHTMap.put("21", new HashSet<String>(Arrays.asList("21", "29")));
        numberHTMap.put("22", new HashSet<String>(Arrays.asList("22", "28")));
        numberHTMap.put("23", new HashSet<String>(Arrays.asList("23", "27")));
        numberHTMap.put("24", new HashSet<String>(Arrays.asList("24", "26")));
        numberHTMap.put("25", new HashSet<String>(Arrays.asList("25")));
    }

    /**
     * 递归方法，当前已抽取的小球个数与要求抽取小球个数相同时，退出递归
     * @param curnum - 当前已经抓取的小球数目
     * @param curmaxv - 当前已经抓取小球中最大的编号
     * @param maxnum - 需要抓取小球的数目
     * @param maxv - 待抓取小球中最大的编号
     */
    public static void kase3(int curnum, int curmaxv,  int maxnum, int maxv){
        if(curnum == maxnum){
            cnt++;
            hMap.put(cnt + "", s.getH());
            tMap.put(cnt + "", s.getT(allSet));
            //System.out.println(cnt);
            return;
        }

        for(int i = curmaxv + 1; i <= maxv; i++){ // i <= maxv - maxnum + curnum + 1
            s.push(i);
            kase3(curnum + 1, i, maxnum, maxv);
            s.pop();
        }
    }
    /*打印数据*/
    private static void printHT() {
        for (Map.Entry<String, Set<String>> hentry : hMap.entrySet()) {
            System.out.println("正" + hentry.getKey() + " : " + hentry.getValue() + " | 反" + hentry.getKey() + " : " + tMap.get(hentry.getKey()));
        }
    }


    /*分堆数量一致需要去重，正反相同那么去除*/
    private static void configHT() {
        Set<String> deleteKey = new HashSet<String>();
        for (Map.Entry<String, Set<String>> hentry : hMap.entrySet()) {
            for (Map.Entry<String, Set<String>> tentry : tMap.entrySet()) {
                if (isCommon(hentry.getValue(), tentry.getValue())) {//正反相同那么去除
                    if (deleteKey.contains(hentry.getKey())) {//如果包含在删除的范围内则跳过
                        continue;
                    }
                    deleteKey.add(tentry.getKey());
                }
            }
        }
        for (String str : deleteKey) {
            hMap.remove(str);
            tMap.remove(str);
        }
    }

    /*判断正反是否相同*/
    private static boolean isCommon(Set<String> hSet, Set<String> tSet) {
        boolean flag = false;
        int count = 0;
        for (String h : hSet) {
            if (tSet.contains(h)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static void createHT(boolean isPrint) {
        createHT(isPrint, 0);
    }

    public static void createHT(boolean isPrint, int shiftCount) {
        kase3(0, 0, 13 + shiftCount, 25);
        Map<String, Set<String>> tempTMap = tMap;
        tMap = convertNumber(hMap);
        hMap = convertNumber(tempTMap);
        if (isPrint) {
            System.out.println("-----------------");
            printHT();
            System.out.println("-----------------");
        }
    }

    /*转换数字*/
    private static Map<String, Set<String>> convertNumber(Map<String, Set<String>> map) {
        Map<String, Set<String>> resultMap = new LinkedHashMap<>();
        Set<String> result = null;
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            result = new LinkedHashSet<>();
            for (String str : entry.getValue()) {
                result.addAll(numberHTMap.get(str));
            }
            resultMap.put(entry.getKey(), result);
        }
        return resultMap;
    }

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        createHT(true, 6);//0-11
        System.out.println((System.currentTimeMillis() - start)/1000 + " 秒");
    }

    private static void test() {
    }

}
