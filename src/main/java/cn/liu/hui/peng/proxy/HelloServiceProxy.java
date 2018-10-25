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
import java.lang.reflect.Method;

/**
 * @author	hz16092620 
 * @date	2018年4月3日 下午5:50:14
 * @version      
 */
public class HelloServiceProxy implements InvocationHandler  {
    
    private Object proxy;
    
    HelloServiceProxy() {
	super();
    }
    
    HelloServiceProxy(Object proxy) {
	this.proxy = proxy;
    }
    
    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
	System.out.println("执行之前。。。。");
	 Object result = method.invoke(proxy, args);
	System.out.println("执行之后。。。。");
	return result;
    }

}
 