/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.string; 

/**
 * @author	hz16092620 
 * @date	2018年4月8日 上午9:49:55
 * @version      
 */
public class TestChangeParam {

    public static void main(String[] args) {
        m1("");
        m1("aaa");
        m1("aaa", "bbb");
        m1("aaa", null);
        String str = null;
        print("aa", str);//别让null值和空值威胁到变长方法，print("aa", null)分不清是调用哪个方法；
    }
    
    public static void m1(String s, String... ss) {
	if (ss != null) {
	    System.out.println("I'm changeParam " + ss);
	    for (int i = 0; i < ss.length; i++) {
		System.out.println(ss[i]);
	    }
	}
	System.out.println("m1");
    }
    
    public static void m1(String s) {
	System.out.println("I'm not changeParam " + s);
    }
    
   public static void print(String test, Integer... is) {
        
    }

    public static void print(String test,String...args ){
          
    }

}
 