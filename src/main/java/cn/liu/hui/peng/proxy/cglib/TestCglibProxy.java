/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.proxy.cglib; 

import org.springframework.cglib.proxy.Enhancer;

/**
 * @author	hz16092620 
 * @date	2018年4月26日 上午11:48:02
 * @version      
 */
public class TestCglibProxy {
    
    public static void main(String[] args) {
	Enhancer en = new Enhancer();
	en.setSuperclass(Requestable.class);
	en.setCallback(new RequestCallback());
	
	Requestable re = (Requestable) en.create();
	re.request();
    }

}
 