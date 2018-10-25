/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.wechat; 

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author	hz16092620 
 * @date	2018年4月18日 下午2:57:03
 * @version      
 */
public class TestDecimal {
    
    public static void main(String[] args) {
	testMoney();
    }
    
    /**
     * 测试金额除以100，即分转换为元，精度不会丢失。
     * */
    public static void testMoney() {
	BigDecimal money = new BigDecimal(13549849);
	BigDecimal result = money.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
	System.out.println(result.toString());
    }

}
 