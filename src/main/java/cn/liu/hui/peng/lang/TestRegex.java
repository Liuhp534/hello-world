package cn.liu.hui.peng.lang; 

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年11月29日 下午1:35:04
 * @version      
 */
public class TestRegex {

    public static void main(String[] args) {
	//testEnter();
	//testReplace();
	testRegex();
    }

    public static void testReplace() {
	String str = "[1,2,3,4,5]";
	Pattern p = Pattern.compile("(\\[|\\])");
	Matcher m = p.matcher(str);
	if (m.matches()) {
	}
	System.out.println(str.replaceAll("\\[|\\]", ""));
    }
    
    public static void testEnter() {
	String line = System.lineSeparator();
	if ("\r\n".equals(line)) {
	    System.out.println("windows");
	} else if ("\n".equals(line)) {
	    System.out.println("Mac");
	} else if ("\r".equals(line)) {
	    System.out.println("linux/unix");
	}
	System.out.print(System.getProperty("line.separator"));// 换行符
	for (int i = 0; i < 10; i++) {
	    System.out.print(i + line);//jvm内部制定的。
	}
    }
    
    public static void testRegex() {
	List<Integer> platforms = new LinkedList<Integer>();
	Pattern p = Pattern.compile("hz16092620");
	Matcher m = p.matcher("hz16092620,hz123123131");
	if (m.matches()) {
	    System.out.println("matches");
	}
	
	if (m.find()) {
	    platforms.add(4);
	}
	System.out.println(platforms);
    }
}
 