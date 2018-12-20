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
		if (methods[i].isAnnotationPresent(Test.class)) {
		    testCount ++;
		    try {
			methods[i].invoke(new AnnotationTest());
		    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
 