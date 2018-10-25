/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.proxy.staticproxy; 

import cn.liu.hui.peng.proxy.UserService;


/**
 * @author	hz16092620 
 * @date	2018年4月26日 上午10:48:26
 * @version      
 */
public class StaticHelloServiceProxy implements UserService {
    
    private UserService userService;
    
    StaticHelloServiceProxy(UserService userService) {
	this.userService = userService;
    }

    @Override
    public String sayMorning(String name) {
	System.out.println("before do someting....");
	String result = userService.sayMorning(name);
	System.out.println("after do something...");
	return "proxy result : " + result;
    }

}
 