/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.decimal; 

import java.text.DecimalFormat;

/**
 * decimalformat 用于格式化数据
 * @author	hz16092620 
 * @date	2018年3月16日 上午10:28:43
 * @version      
 */
public class TestDecimal {
    
    public static void main(String[] args) {
	decimal();
    }
    
    private static void decimal() {
	DecimalFormat format = new DecimalFormat("JM0000");
	Integer i = 114;
	System.out.println(format.format(i));
    }

}
 