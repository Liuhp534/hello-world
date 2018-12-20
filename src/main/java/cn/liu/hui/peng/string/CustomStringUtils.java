package cn.liu.hui.peng.string; 

import java.util.ArrayList;
import java.util.List;

/**
 * 一些常用的字符串工具类
 * @author	hz16092620 
 * @date	2018年4月8日 上午11:17:32
 * @version      
 */
public class CustomStringUtils {
    
    public static void main(String[] args) {
	List<String> list = new ArrayList<>();
	list.add("a");
	list.add("b");
	list.add("c");
	list.add("d");
	CustomStringUtils.print(list);
	System.out.println(CustomStringUtils.isNumber("1231"));
    }
    
    /**
     * 打印输出集合
     * */
    public static void print(List<?> list) {
	if (list != null) {
	    for (int i = 0; i < list.size(); i++) {
		System.out.println(list.get(i));
            }
	}
    }
    
    /**
     * 判断字符串是否是数字
     * */
    public static boolean isNumber(String str) {
	if (CustomStringUtils.isNotEmpty(str)) {
	    for (int i = 0; i < str.length(); i++) {
	        if (!Character.isDigit(str.charAt(i))) {
	            return false;
	        }
            }
	}
	return true;
    }
    
    /**
     * 判断字符串是否为空
     * */
    public static boolean isEmpty(String str) {
	if (str == null || str.length() == 0) {
	    return true;
	}
	return false;
    }
    
    /**
     * 判断字符串是否不为空
     * */
    public static boolean isNotEmpty(String str) {
	return !isEmpty(str);
    }
}
 