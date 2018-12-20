package cn.liu.hui.peng.string; 

/**
 * 字符串String不可变的字符串，内部实现是基于char[]数组，
 * 1、因为只能内部访问，所以不可变；
 * 2、final类
 * 3、如何实现一个不可变类
a、将类声明为final，所以它不能被继承。
b、将所有的成员声明为私有的，这样就不允许直接访问这些成员。
c、对变量不要提供setter方法。
d、将所有可变的成员声明为final，这样只能对它们赋值一次。
e、通过构造器初始化所有成员，进行深拷贝(deep copy)。
f、在getter方法中，不要直接返回对象本身，而是克隆对象，并返回对象的拷贝。
 * 4、其他的方法有，equals toLowerCase split format格式化字符串，可以动态的拼接字符串，比如 数字 特殊字符什么的。
 * @author	hz16092620 
 * @date	2018年4月4日 下午4:59:21
 * @version      
 */
public class TestBaseString {
    
    
    public static void main(String[] args) {
	//test();
	//testCharSequence();
	//testStartsAndEndWith();
	//testContact();
	//testReplace();
	//testTrim();
	//testIntern();
	//testStringAdd();
	//endWith();
	String str = "Ａ";
	String str2 = "A";
	System.out.println(str.codePointAt(0));
	System.out.println(str2.codePointAt(0));
    }
    
    static void endWith() {
	String str = "6222021000014856233";
	System.out.println(str.endsWith("3"));
    }
    
    /**
     * 比较字符串大小
     * */
    static void testStringAdd() {
	String s1 = new String("hello");
	String s2 = new String("world");
	String s3 = s1 + " " + s2;
	String s4 = "hello world";
	System.out.println(s3==s4);
    }
    
    
    
    /**
     * 测试trim。
     * intern方法是Native调用，它的作用是在方法区中的常量池里通过equals方法寻找等值的对象，
     * 如果没有找到则在常量池中开辟一片空间存放字符串并返回该对应String的引用，
     * 否则直接返回常量池中已存在String对象的引用。
     * */
    static void testIntern() {
	String str = "liuhuipeng";
	String str1 = new String("liuhuipeng").intern();
	String str2 = "liuhuipeng";
	String str3 = new String("liuhuipeng").intern();
	System.out.println(str==str1);//true
	System.out.println(str==str2);//true
	System.out.println(str1==str3);//true
    }
    
    /**
     * 测试trim。
     * */
    static void testTrim() {
	String str = " liuhuipeng ";
	System.out.println(str.trim());
    }
    
    /**
     * 测试replace。
     * */
    static void testReplace() {
	String str = "liuhuipeng";
	System.out.println(str.replace('l', 'L'));
    }
    
    /**
     * 测试开头和结尾的字符。
     * */
    static void testStartsAndEndWith() {
	String str = "liuhuipeng";
	System.out.println(str.startsWith("iuh", 1));
	System.out.println(str.endsWith("peng"));
    }
    
    /**
     * 测试拼接方法。
     * */
    static void testContact() {
	String str = "liuhuipeng";
	System.out.println(str.concat("dashuaige"));
    }
    
    /**
     * 测试++，后面的话就是先返回再计算。
     * */
    static void testAdd() {
	int i = 5;
	System.out.println(i++);
    }
    
    /**
     * 测试charAt方法、indexOf。
     * */
    static void test() {
	String str = "hello string";
	System.out.println(str + " 索引为2的字符 " +str.charAt(2));
	System.out.println(str + " 索引为0的代码点 " +str.codePointAt(0));
	System.out.println(str + " 字符串e的位置 " +str.indexOf("e"));
	System.out.println(str + " 字符e的位置 " +str.indexOf('e'));
	System.out.println(str + " 字符串e的最后的位置 " +str.lastIndexOf("e"));
	System.out.println(str + " 字符e的最后的位置 " +str.lastIndexOf('e'));
    }
    
    /**
     * charSequence和三个实现类
     */ 
    static void testCharSequence() {
	CharSequence cs = "aa";
	String s1 = "aa";
	StringBuffer sbu = new StringBuffer("aa");
	StringBuilder sb = new StringBuilder("aa");
	System.out.println("stringbuffer and string " + sbu.equals(sb));
	System.out.println("CharSequence and string " + cs.equals(s1));
	System.out.println("StringBuffer and string " + sbu.equals(s1));
	System.out.println("StringBuilder and string " + sb.equals(s1));
	
	System.out.println("string and CharSequence " + s1.contentEquals(cs));
	System.out.println("string and StringBuffer " + s1.contentEquals(sbu));
	System.out.println("string and StringBuilder " + s1.contentEquals(sb));
    }

}
 