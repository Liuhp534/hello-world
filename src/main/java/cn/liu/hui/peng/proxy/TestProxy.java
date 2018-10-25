/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.proxy; 

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author	hz16092620 
 * @date	2018年4月23日 下午7:54:31
 * @version      
 */
public class TestProxy {

    public static void main(String[] args) {
	UserService service = new UserServiceImpl();
	//实现特定业务的代理类
	InvocationHandler proxy = new HelloServiceProxy(service);
	//参数和具体的实现类对象没有关系，第二个参数UserService.class.getInterfaces()获取的是接口的接口，不要理错了。
	service = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[] {UserService.class}, proxy);
	service.sayMorning("aaa");
    }
    
    
}
 