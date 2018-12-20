package cn.liu.hui.peng.string; 

import java.util.StringTokenizer;

/**
 * 字符串操作的高级方法
 * @author	hz16092620 
 * @date	2018年4月8日 下午3:36:34
 * @version      
 */
public class TestSeniorString {
    
    public static void main(String[] args) {
	//testTrimSenior();
	//testTrimReplaceAll();
	//testStringBuilder();
	//testDeleteRepeatChar();
	//testStringBuilderReverse();
	testTrimBase();
    }
    
    /**
     * 测试字符串是否回文，即前后读都是一样的
     * */
    static void testStringBuilderReverse() {
	String str = "abcba";
	StringBuilder sb1 = new StringBuilder(str);
	sb1.reverse();
	System.out.println("sb1 " + sb1);
	for (int i = 0; i < str.length(); i++) {
	    if (!(str.charAt(i) == sb1.charAt(i))) {
		System.out.println("不是回文");
		break;
	    }
	}
    }
    
    /**
     * 去除重复的字符串/字符，原理就是利用StringBuilder可变的特性
     * */
    static void testDeleteRepeatChar() {
	String str = "liuhuipengliuhuipeng";
	StringBuilder sb = new StringBuilder(str);
	for (int i = 0; i < sb.length(); i++) {
	   for (int j = i+1; j < sb.length(); j++) {
	       if (sb.charAt(i) == sb.charAt(j)) {
		   sb.deleteCharAt(j);
	       }
	   }
        }
	System.out.println(sb.toString());
    }
    
    /**
     * 将两个字符串拼接，删除
     * */
    static void testStringBuilder() {
	String s1 = "int";
	String s2 = "ser";
	StringBuilder sb = new StringBuilder(s1);
	sb.insert(2, s2);
	System.out.println(sb.toString());
	System.out.println(sb.delete(1, 3));//这个其实和时间那个是一样的，1-2,2-3=2个
    }
    
    /**
     * 去除空格，方法一。
     * */
    static void testTrimSenior() {
	String str = " we are family ";
	StringTokenizer st = new StringTokenizer(str, " ");
	StringBuilder sb = new StringBuilder();
	while (st.hasMoreElements()) {
	    sb.append(st.nextElement());
        }
	System.out.println(sb.toString());
    }

    /**
     * 去除空格，方法二。
     * */
    static void testTrimReplaceAll() {
	String str = " we are family ";
	System.out.println("replaceAll(正则表达式)=" + str.replaceAll(" ", ""));
	System.out.println("replace=" + str.replace(" ", ""));
    }
    
    /**
     * 去除空格，方法三。
     * */
    static void testTrimBase() {
	String str = " we are family ";
	StringBuilder sb = new StringBuilder(str);
	char empty = ' ';
	for (int i = 0; i < sb.length(); i++) {
	    if (sb.charAt(i) == empty) {
		sb.deleteCharAt(i);
	    }
        }
	System.out.println("通过StringBuilder去除空格 " + sb.toString());
	String[] strs = str.split(" ");
	sb = new StringBuilder();
	for (int i = 0; i < strs.length; i++) {
	    sb.append(strs[i]);
        }
	System.out.println("通过split去除空格 " + sb.toString());
    }
}
 