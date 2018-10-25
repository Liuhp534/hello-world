/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.annotation_custom; 

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>
 * 
 *
 * 测试注解类
 * </p>
 * @author	hz16092620 
 * @date	2018年9月6日 下午6:19:31
 * @version      
 */
public class AnnotationTest {
    
    @Test
    public static void m1() {
	
    }
    
    @Test 
    public static void m2() {
	throw new RuntimeException();
    }
    
    
    @Test
    public void m3() {
	
    }
    
    public void m4() {
	
    }
    
    @ExceptionTest({IndexOutOfBoundsException.class})//NullPointerException.class, 
    public void m5() {
	String str = null;
	System.out.println(str.length());
    }
    
    public static void main(String[] args) {
	String className = "cn.liu.hui.peng.annotation_custom.AnnotationTest";
	int testCount = 0;
	int errorCount = 0;
	try {
	    Class<?> c = Class.forName(className);
	    Method[] methods = c.getDeclaredMethods();
	    for (int i = 0; i < methods.length; i++) {
		/*if (methods[i].isAnnotationPresent(Test.class)) {
		    testCount ++;
		    try {
			methods[i].invoke(new AnnotationTest());
		    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			errorCount ++;
		    }
		}*/
		if (methods[i].isAnnotationPresent(ExceptionTest.class)) {
		    testCount ++;
		    try {
			methods[i].invoke(new AnnotationTest());
		    } catch (Throwable wrappedExp) {//Throwable 可以包装异常
			Throwable exc = wrappedExp.getCause();
			Class<? extends Exception>[] excTypes = methods[i].getAnnotation(ExceptionTest.class).value();//方法获取其注解，进而获取注解中的信息
			for (Class<? extends Exception> cl : excTypes) {//判断
			    if (cl.isInstance(exc)) {
				System.out.println(exc.getMessage());
				System.out.println(methods[i].getName() + " is the " + c.getName());
			    }
			}
			errorCount ++;
		    }
		}
	    }
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	System.out.println("testCount : " + testCount + " errorCount : " + errorCount);
    }

}
 