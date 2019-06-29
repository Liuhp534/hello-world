package cn.liu.hui.peng.excel.ticket;

import java.util.*;

/**
 * @description: 生肖和数字
 * @author: liuhp534
 * @create: 2019-06-22 20:12
 */
public class AnimalAndNumber {

    private static Map<String, String> dataMap = new HashMap<>();
    //[牛, 蛇, 马, 羊, 猴, 鸡] 70
    //[牛, 蛇, 马, 羊, 鸡, 狗] 71
    static {
        dataMap.put("鼠", "12,24,36,48");
        dataMap.put("牛", "11,23,35,47");
        dataMap.put("虎", "10,22,34,46");
        dataMap.put("兔", "09,21,33,45");
        dataMap.put("龙", "08,20,32,44");
        dataMap.put("蛇", "07,19,31,43");

        dataMap.put("马", "06,18,30,42");
        dataMap.put("羊", "05,17,29,41");
        dataMap.put("猴", "04,16,28,40");
        dataMap.put("鸡", "03,15,27,39");
        dataMap.put("狗", "02,14,26,38");
        dataMap.put("猪", "01,13,25,37,49");
    }


    public static void main(String[] args) {
        String paramStr = "01,02,03,06,07,11,15,17,18,19,23,26,27,28,29,30,31,35,39,41,42,43,47,49";

        numberToAnimal(paramStr);
    }

    public static void numberToAnimal(Set<String> paramSet) {
        StringBuilder sb = new StringBuilder();
        for (String str : paramSet) {
            sb.append(str).append(",");
        }
        numberToAnimal(sb.toString());
    }

    public static void numberToAnimal(String paramStr) {
        Map<String, Set<String>> map1 = new LinkedHashMap<>();
        String[] str1 = paramStr.split(",");
        for (int i = 0; i < str1.length; i ++) {
            for (Map.Entry<String, String> entry : dataMap.entrySet())
                if (entry.getValue().indexOf(str1[i]) != -1) {
                    if (map1.get(entry.getKey()) != null) {
                        map1.get(entry.getKey()).add(str1[i]);
                    } else {
                        HashSet<String> set = new HashSet<>();
                        set.add(str1[i]);
                        map1.put(entry.getKey(), set);
                    }
                }
        }
        //打印
        for (Map.Entry<String, Set<String>> entry : map1.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("===============");
    }

}
