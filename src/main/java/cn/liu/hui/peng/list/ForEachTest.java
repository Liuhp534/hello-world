package cn.liu.hui.peng.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 *
 *
 * </p>
 *
 * @author hz16092620
 * @date 2018年10月6日 上午10:20:22
 */
public class ForEachTest {

    public static void main(String[] args) {
        //forEach();
        //applyStyle(1 | 2);
        //forInnerBreak();
        insureNumShard();
    }

    /**
     * 机制是先创建一个数组，大小为参数个数，无参数则一个为0的空数组不是null。
     */
    static void forEach(int... ints) {
        System.out.println(ints.length);
        for (int i : ints) {
            System.out.println(i);
        }
    }

    static void applyStyle(int i) {
        System.out.println(i);
    }

    static void forInnerBreak() {
        for (int i = 0; i < 10; i++) {
            System.out.println("break before" + i);
            if (i == 2) {
                if (i > 1) {
                    break;
                }
            }
            System.out.println("break after" + i);
        }
        System.out.println("for after");
    }

    static void insureNumShard() {
        String insureNum = "20190305001069";
        List<String> insureNums = new ArrayList<>();
        insureNums.add("20190111012069");
        insureNums.add("20190111013874");
        insureNums.add("20190111014261");
        insureNums.add("20190111009764");
        insureNums.add("20190111012709");
        insureNums.add("20190111016502");
        insureNums.add("20190111017460");
        insureNums.add("20190111010385");
        insureNums.add("20190111016407");
        insureNums.add("20190111017220");
        insureNums.add("20190111018465");
        insureNums.add("20190111024742");
        insureNums.add("20190111019400");
        insureNums.add("20190111021944");
        insureNums.add("20190111022151");
        insureNums.add("20190111019617");
        insureNums.add("20190111019804");
        insureNums.add("20190111019980");
        insureNums.add("20190111020059");
        insureNums.add("20190111020440");
        insureNums.add("20190111020489");
        insureNums.add("20190111020894");
        insureNums.add("20190111021487");
        insureNums.add("20190111021645");
        insureNums.add("20190111019371");
        insureNums.add("20190111022188");
        insureNums.add("20190111023043");
        insureNums.add("20190111023282");
        insureNums.add("20190111024006");
        insureNums.add("20190111024337");
        insureNums.add("20190111024377");
        insureNums.add("20190111018112");
        insureNums.add("20190111018364");
        insureNums.add("20190111024965");
        insureNums.add("20190111025193");
        insureNums.add("20190111025216");
        insureNums.add("20190111025219");
        insureNums.add("20190111025502");
        insureNums.add("20190111026128");
        insureNums.add("20190111026496");
        insureNums.add("20190111027446");
        insureNums.add("20190111027649");
        insureNums.add("20190111030074");
        insureNums.add("20190111026239");
        Map<Integer, List<String>> resultMap = new HashMap<>();
        for (int i = 0; i < insureNums.size(); i++) {
            int temp = insureNums.get(i).toString().hashCode();
            if (temp < 0) {
                temp = -temp;
            }
            if (resultMap.get(temp % 30) != null) {
                resultMap.get(temp % 30).add(insureNums.get(i));
            } else {
                List<String> list = new ArrayList<>();
                list.add(insureNums.get(i));
                resultMap.put(temp % 30, list);
            }
        }

        for (Map.Entry<Integer, List<String>> entry :resultMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
 