package cn.liu.hui.peng.list; 

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月6日 上午10:20:22
 * @version      
 */
public class ForEachTest {
    
    public static void main(String[] args) {
	//forEach();
	applyStyle(1 | 2);
    }

    /**机制是先创建一个数组，大小为参数个数，无参数则一个为0的空数组不是null。*/
    static void forEach(int ... ints) {
	System.out.println(ints.length);
	for (int i : ints) {
	    System.out.println(i);
	}
    }
    
    static void applyStyle(int i) {
	System.out.println(i);
    }
}
 