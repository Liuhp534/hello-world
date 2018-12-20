package cn.liu.hui.peng.wheel; 

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 
 * 正则表达式工具
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年12月2日 下午3:19:27
 * @version      
 */
public class RegexUtils {
    
    public static void main(String[] args) {
	System.out.println("----------------------------------validUnsignInteger------------------------------");
	System.out.println("validUnsignInteger null " + validUnsignInteger(null));
	System.out.println("validUnsignInteger \"\" " + validUnsignInteger(""));
	System.out.println("validUnsignInteger 123 " + validUnsignInteger("123"));
	System.out.println("validUnsignInteger -123 " + validUnsignInteger("-123"));
	System.out.println("validUnsignInteger 123a " + validUnsignInteger("123a"));
	System.out.println("validUnsignInteger 0 " + validUnsignInteger("0"));
	System.out.println("----------------------------------validNegativeInteger------------------------------");
	System.out.println("validNegativeInteger null " + validNegativeInteger(null));
	System.out.println("validNegativeInteger \"\" " + validNegativeInteger(""));
	System.out.println("validNegativeInteger 123 " + validNegativeInteger("123"));
	System.out.println("validNegativeInteger -123 " + validNegativeInteger("-123"));
	System.out.println("validNegativeInteger 123a " + validNegativeInteger("123a"));
	System.out.println("validNegativeInteger 0 " + validNegativeInteger("0"));
	System.out.println("----------------------------------validIsInclude------------------------------");
	System.out.println("validIsInclude param=1, str=123 " + validIsInclude("1", "123"));
	System.out.println("validIsInclude param=123, str=123 " + validIsInclude("123", "123"));
	System.out.println("validIsInclude param=1234, str=123 " + validIsInclude("1234", "123"));
	System.out.println("----------------------------------validIsMatcher------------------------------");
	System.out.println("validIsMatcher param=\\w*, str=123 " + validIsMatcher("\\w*", "123"));
	System.out.println("validIsMatcher param=1, str=123 " + validIsMatcher("1", "123"));
	System.out.println("validIsMatcher param=123, str=123 " + validIsMatcher("123", "123"));
	System.out.println("validIsMatcher param=1234, str=123 " + validIsMatcher("1234", "123"));
    }
    
    /**
     * <p>
     * 
     *  验证str是否匹配regexStr正则字符串
     *
     * </p>
     * @param regexStr
     * @param str
     * @return
     *  
     * @author	hz16092620 
     * @date	2018年12月2日 下午3:33:30
     * @version      
     */ 
    public static boolean validIsMatcher(String regexStr, String str) {
	if (str == null || regexStr == null) {
	    return false;
	}
	/*//一般方法
	Pattern p = Pattern.compile(regexStr);
	Matcher m = p.matcher(str);
	if (m.matches()) {
	    return true;
	}
	return false;
	*/
	//快速方法
	return Pattern.matches(regexStr, str);
    }
    
    /**
     * <p>
     * 
     * 验证str是否包含regexStr正则字符串
     *
     * </p>
     * @param regexStr
     * @param str
     * @return
     *  
     * @author	hz16092620 
     * @date	2018年12月2日 下午3:29:17
     * @version      
     */ 
    public static boolean validIsInclude(String regexStr, String str) {
	if (str == null || regexStr == null) {
	    return false;
	}
	Pattern p = Pattern.compile(regexStr);
	Matcher m = p.matcher(str);
	if (m.find()) {
	    return true;
	}
	return false;
    }
    
    /**
     * <p>
     * 
     * 验证字符串的值是否是非负整数
     *
     * </p>
     * @param str
     * @return
     *  
     * @author	hz16092620 
     * @date	2018年12月2日 下午3:25:42
     * @version      
     */ 
    public static boolean validUnsignInteger(String str) {
	if (str == null) {
	    return false;
	}
	Pattern p = Pattern.compile("^[1-9]+[0-9]*|0$");//^和$不参与|的运算
	Matcher m = p.matcher(str);
	if (m.matches()) {
	    for (int i=0; i < m.groupCount(); i++) {
		System.out.println(m.group(i+1));
	    }
	    System.out.println(m.groupCount());
	    return true;
	}
	return false;
    }
    
    /**
     * <p>
     * 
     * 验证字符串的值是否是负正整数
     *
     * </p>
     * @param str
     * @return
     *  
     * @author	hz16092620 
     * @date	2018年12月2日 下午3:25:42
     * @version      
     */ 
    public static boolean validNegativeInteger(String str) {
	if (str == null) {
	    return false;
	}
	Pattern p = Pattern.compile("^((-\\d+)|(0+))$");//^-[1-9]\d*|0$ 或 ^((-\d+)|(0+))$
	Matcher m = p.matcher(str);
	if (m.matches()) {
	    System.out.println(m.groupCount());//几个括号就几个组
	    for (int i=0; i < m.groupCount(); i++) {
		System.out.println(m.group(i));
	    }
	    return true;
	}
	return false;
    }

}
 