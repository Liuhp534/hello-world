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
 * @date	2018年8月16日 上午10:06:12
 * @version      
 */
public class ReplaceStr {
    
    public static void main(String[] args) {
	System.out.println(replaceChar("6789", 1, 4));
	System.out.println("6789@qq.com".indexOf("@"));
	System.out.println(replaceChar("6789@qq.com", 1, 4));
    }
    
    
    public static String replaceChar(String orgString,int startIndex,int lastNum){
	if(orgString != null){
	    startIndex = startIndex - 1;
	    StringBuilder str = new StringBuilder(orgString.length());
	    for(int i = 0;i< orgString.length() ;i ++){
	        if((orgString.length()-lastNum)<=i){
	            str.append(orgString.charAt(i));
	        }else {
	            if (i >= startIndex) {
		        str.append("*");
	            } else {
		        str.append(orgString.charAt(i));
	            }
	        }
	    }
	    return str.toString();
	}
	return null;
    }

}
 