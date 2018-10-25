/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.extend; 

import java.util.Date;

/**
 * @author	hz16092620 
 * @date	2018年7月26日 下午8:13:19
 * @version      
 */
public class Sub extends Super {
    
    private final Date date = new Date(); 
    
    public Sub() {
	super();
    }

    @Override
    public void overrideMe() {
	System.out.println(date);
    }
    
    /**
     * 超类调用超类的方法，子类重写的话，父类初始化构造方法的时候，会调用重写的那个。
     * */
    public static void main(String[] args) {
	Super s = new Sub();
	s.overrideMe();
    }
    
}
 