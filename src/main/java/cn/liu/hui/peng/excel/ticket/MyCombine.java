package cn.liu.hui.peng.excel.ticket;

import cn.liu.hui.peng.excel.TicketData;
import cn.liu.hui.peng.excel.ticket.number.NumberJdbcUtils;

import java.util.*;

public class MyCombine {

    public static Map<String, Set<String>> hMap = new LinkedHashMap<>();;
    public  static Map<String, Set<String>> tMap = null;
    static List<TicketData> ticketDatas;/*历史数据*/

    public static void main(String[] args) {
        //numberAnlaynis();
       animalAnlaynis();
        //allContains();
        //allContainsNow();
    }

    private static void allContainsNow() {
        long start = System.currentTimeMillis();
        int num = 5;
        MyCombine tp = new MyCombine();
        tp.combine(allNumbers, num);

        /*for (Map.Entry<String, Set<String>> entry : hMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }*/
        ticketDatas = NumberJdbcUtils.getAll();
        Map<Integer, Integer> numberCountMap = new TreeMap<>();
        int maxContinue = 5;
        for (Map.Entry<String, Set<String>> entry : hMap.entrySet()) {
            int count = 0;
            for (TicketData ticketData : ticketDatas) {
                if (entry.getValue().contains(ticketData.getSpecial())) {
                    count ++;
                } else {
                    if (count >= maxContinue) {
                        if (null != numberCountMap.get(count)) {
                            numberCountMap.put(count, numberCountMap.get(count) + 1);
                        } else {
                            numberCountMap.put(count, 1);
                        }
                    }
                    count = 0;
                    break;
                }
            }
        }
        System.out.println("连续" + maxContinue  + "的数据=" + numberCountMap);
        System.out.println("耗时=" + (System.currentTimeMillis() - start));
    }

    private static void allContains() {
        long start = System.currentTimeMillis();
        int num = 5;
        MyCombine tp = new MyCombine();
        tp.combine(allNumbers, num);

        /*for (Map.Entry<String, Set<String>> entry : hMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }*/
        ticketDatas = NumberJdbcUtils.getAll();
        Map<Integer, Integer> numberCountMap = null;
        String maxContinue = null;
        for (Map.Entry<String, Set<String>> entry : hMap.entrySet()) {
            int count = 0;
            numberCountMap = new TreeMap<>();
            for (TicketData ticketData : ticketDatas) {
                if (entry.getValue().contains(ticketData.getSpecial())) {
                    count ++;
                } else {
                    if (count > 0) {
                        if (null != numberCountMap.get(count)) {
                            numberCountMap.put(count, numberCountMap.get(count) + 1);
                        } else {
                            numberCountMap.put(count, 1);
                        }
                    }
                    count = 0;
                }
            }
            if (numberCountMap.size() > 0) {
                maxContinue = numberCountMap.keySet().toArray()[numberCountMap.size() -1].toString();
                if (Integer.valueOf(maxContinue) > (num + 1)) {
                    System.out.println(entry.getKey() + "=" + entry.getValue() + "=" + numberCountMap);
                }
            }
        }
        System.out.println("耗时=" + (System.currentTimeMillis() - start));
    }

    private static void numberAnlaynis() {
        long start = System.currentTimeMillis();
        String[] a = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"
                , "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49"};
        int num = 3;
        MyCombine tp = new MyCombine();
        tp.combine(a, num);

        /*for (Map.Entry<String, Set<String>> entry : hMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }*/
        ticketDatas = NumberJdbcUtils.getAll();
        Map<Integer, Integer> numberCountMap = null;
        for (Map.Entry<String, Set<String>> entry : hMap.entrySet()) {
            int count = 0;
            numberCountMap = new TreeMap<>();
            for (TicketData ticketData : ticketDatas) {
                if (containsAll(ticketData, entry.getValue())) {
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
            if (numberCountMap.size() > 0) {
                System.out.println(entry.getKey() + "=" + entry.getValue() + "=" + numberCountMap);
            }
        }
        System.out.println("耗时=" + (System.currentTimeMillis() - start));
    }

    private static boolean containsAll(TicketData ticketData, Set<String> numbers) {
        int containsCount = 0;
        for (String str : numbers) {
            if (ticketData.getAllNumberSet().contains(str)) {
                containsCount ++;
            }
        }
        //return containsCount == numbers.size() ? true : false;
        return containsCount > 1 ? true : false;
    }

    private static void animalAnlaynis() {
        MathStack.createHT(Boolean.FALSE, 2);
        Map<String, Set<String>> tMap = MathStack.tMap;
        ticketDatas = JdbcUtils.getAll();
        Map<Integer, Integer> numberCountMap = null;
        Map<String, Integer> animalCountMap = null;
        int temp = 0;
        Map<String, Integer> minNumberMap = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : tMap.entrySet()) {
            int count = 0;
            numberCountMap = new TreeMap<>();
            for (TicketData ticketData : ticketDatas) {
                if (containsAll2(ticketData, entry.getValue())) {
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
                if (Integer.valueOf(numberCountMap.keySet().toArray()[0].toString()) == 0) {
                    minNumberMap.put(entry.getKey(), Integer.valueOf(numberCountMap.keySet().toArray()[0].toString()));
                }
                temp ++;
                //System.out.println(entry.getKey() + "=" + "" + "=" + numberCountMap);//entry.getValue()//什么是热数据，这里面越多的？这可以是个维度
            }
        }
        System.out.println("总组合=" + tMap.size() + ", 大于0的=" + temp);
        //System.out.println(minNumberMap.size());
        for (TicketData ticketData : ticketDatas) {
            animalCountMap = new TreeMap<>();
            filterThreeAnimal(ticketData, animalCountMap);
        }
        System.out.println(threeAnimalCount);
    }


    static int threeAnimalCount = 0;
    private static void filterThreeAnimal(TicketData ticketData, Map<String, Integer> animalCountMap) {
        for (String tempAnimal : ticketData.getAllAnimalList()) {//获取连续三个的
            if (null != animalCountMap.get(tempAnimal)) {
                animalCountMap.put(tempAnimal, animalCountMap.get(tempAnimal) + 1);
            } else {
                animalCountMap.put(tempAnimal, 1);
            }
        }
        //只保留连续三个的
        Set<String> tempAnimalSet = animalCountMap.keySet();
        Iterator<String> animalIt = tempAnimalSet.iterator();
        while (animalIt.hasNext()) {
            if (animalCountMap.get(animalIt.next()) < 4) {
                animalIt.remove();
            }
        }
        if (animalCountMap.size() > 0) {
            //System.out.println(ticketData.getPeriodNum() + "=" + animalCountMap);
            threeAnimalCount ++;
        }
    }

    private static boolean containsAll2(TicketData ticketData, Set<String> animals) {
        int containsCount = 0;
        for (String str : animals) {
            if (ticketData.getAllAnimalSet().contains(str)) {
                containsCount ++;
            }
        }
        return containsCount == animals.size() ? true : false;
        //return containsCount > 2 ? true : false;
    }

    /**
     * 实现的算法
     *
     * @param a   数据数组
     * @param num M选N中 N的个数
     * @return
     */
    private void combine(String[] a, int num) {
        //List<String> list = new ArrayList<String>();
        //StringBuffer sb = new StringBuffer();
        String[] b = new String[a.length];
        for (int i = 0; i < b.length; i++) {
            if (i < num) {
                b[i] = "1";
            } else
                b[i] = "0";
        }

        int point = 0;
        int nextPoint = 0;
        int count = 0;
        int sum = 0;
        String temp = "1";
        int ticketNum = 0;
        Set<String> ticketData = null;
        while (true) {
            ticketNum ++;
            ticketData = new LinkedHashSet<>();
            // 判断是否全部移位完毕
            for (int i = b.length - 1; i >= b.length - num; i--) {
                if (b[i].equals("1"))
                    sum += 1;
            }
            // 根据移位生成数据
            for (int i = 0; i < b.length; i++) {
                if (b[i].equals("1")) {
                    point = i;
                    /*sb.append(a[point]);
                    sb.append(" ");*/
                    ticketData.add(a[point]);
                    count++;
                    if (count == num)
                        break;
                }
            }
            // 往返回值列表添加数据
            hMap.put(ticketNum + "", ticketData);
            //list.add(sb.toString());

            // 当数组的最后num位全部为1 退出
            if (sum == num) {
                break;
            }
            sum = 0;

            // 修改从左往右第一个10变成01
            for (int i = 0; i < b.length - 1; i++) {
                if (b[i].equals("1") && b[i + 1].equals("0")) {
                    point = i;
                    nextPoint = i + 1;
                    b[point] = "0";
                    b[nextPoint] = "1";
                    break;
                }
            }
            // 将 i-point个元素的1往前移动 0往后移动
            for (int i = 0; i < point - 1; i++)
                for (int j = i; j < point - 1; j++) {
                    if (b[i].equals("0")) {
                        temp = b[i];
                        b[i] = b[j + 1];
                        b[j + 1] = temp;
                    }
                }
            // 清空 StringBuffer
            //sb.setLength(0);
            count = 0;
        }
        //
        System.out.println("数据长度 " + hMap.size());
        //return list;

    }

    static String[] allNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"
            , "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49"};
}