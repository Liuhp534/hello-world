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
 * @date	2018年5月31日 上午10:43:10
 * @version      
 */
public class TestSwicth {
    public static void main(String[] args) {
	String key = "1";
	
	switch (key) {
	    case "1":
		System.out.println("1");
		break;
	    case "2":
		System.out.println("2");
		break;
	    case "3":
		System.out.println("3");
		break;
	    case "4":
		System.out.println("4");
		break;
	    default:
		System.out.println("...");
		break;
	}
    }

}
 