/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.lang; 

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月24日 上午10:43:48
 * @version      
 */
public class TestFinal {
    
    
    private final String name;
    
    private String configName;//通过spring value注入值
    
    {
	this.name = configName;
    }
    
  /*  TestFinal (String name) {
	this.name = name;
    }*/
    
    public void fun() {
	final String str;
	str = "good";
	
	if (str.equals("good")) {
	    //str = "very";//compile error
	}
    }

    public void fun1(final String str) {
	//str = "good";//compile error
    }
}

final class FinalDog {
    
    private final String name;
    
    FinalDog (String name) {
	this.name = name;
    }
}
 