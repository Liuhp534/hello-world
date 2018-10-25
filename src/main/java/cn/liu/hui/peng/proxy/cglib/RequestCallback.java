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

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author	hz16092620 
 * @date	2018年4月26日 上午11:42:34
 * @version      
 */
public class RequestCallback implements MethodInterceptor {

    @Override
    public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
	System.out.println("before");
	Object result = arg3.invokeSuper(arg0, arg2);
	System.out.println("after");
	return result;
    }


}
 