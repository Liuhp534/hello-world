/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.locale; 

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 本地化工具NumberFormat、DateFormat、MessageFormat
 * @author	hz16092620 
 * @date	2018年4月19日 下午3:21:43
 * @version      
 */
public class TestLocale {
    
    public static void main(String[] args) {
	//testLocale();
	testDateFormat();
    }
    
    /**
     * NumberFormat转换金额
     * */
    public static void testLocale() {
	Locale locale = Locale.US;
	NumberFormat number = NumberFormat.getCurrencyInstance(locale);
	double amt = 123456.78;
	System.out.println(number.format(amt));
    }

    /**
     * DateFormat转换金额
     * */
    static void testDateFormat() {
	//Locale locale = new Locale("en", "US");    
	Locale locale = new Locale("zh", "CN");    
	Date date = new Date();    
	DateFormat df = DateFormat.getDateInstance(DateFormat.ERA_FIELD, locale);    
	System.out.println(df.format(date));
    }

}
 