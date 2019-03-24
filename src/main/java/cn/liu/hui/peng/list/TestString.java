package cn.liu.hui.peng.list;

/**
 * <p>
 *
 *
 *
 * </p>
 *
 * @author hz16092620
 * @date 2018年11月17日 下午5:45:57
 */
public class TestString {

    public static void main(String[] args) {
        testHeap();
    }

    /**
     * 查看字符串占用内存大小
     */
    public static void testHeap() {
        String str = "";
        String temp = "hz16092620,";
        for (int i = 0; i < 200; i++) {
            str += temp;
        }
        System.out.println(str);
        System.out.println("占用内存大小：" + str.getBytes().length);
    }
}
 