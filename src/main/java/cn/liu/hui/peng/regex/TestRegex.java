package cn.liu.hui.peng.regex; 

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年11月25日 下午8:29:13
 * @version      
 */
public class TestRegex {

    public static void main(String[] args) {
	String s = "///aaa\\ccc\\\\";
	System.out.println(s);
	System.out.println(s.replace("\\", "\\\\"));
	System.out.println(s.replaceAll("\\\\", "\\\\\\\\"));
    }
}
 