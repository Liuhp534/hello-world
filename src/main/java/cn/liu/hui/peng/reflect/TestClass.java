/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.reflect; 

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 了解反射
 * @author	hz16092620 
 * @date	2018年4月3日 下午3:36:15
 * @version      
 */
public class TestClass {
    
    public static void main(String[] args) throws Exception {
	//constructors();
	//fields();
	methods();
    }
    
    public static void methods() throws Exception {
	//1.获取Class对象  
        Class stuClass = Class.forName("cn.liu.hui.peng.invoke.Student");  
        //2.获取所有公有方法  
        System.out.println("***************获取所有的”公有“方法*******************");  
        stuClass.getMethods();  
        Method[] methodArray = stuClass.getMethods();  
        for(Method m : methodArray){  
            System.out.println(m);  
        }  
        System.out.println("***************获取所有的方法，包括私有的*******************");  
        methodArray = stuClass.getDeclaredMethods();  
        for(Method m : methodArray){  
            System.out.println(m);  
        }  
        System.out.println("***************获取公有的show1()方法*******************");  
        Method m = stuClass.getMethod("show1", String.class);  
        System.out.println(m);  
        //实例化一个Student对象  
        Object obj = stuClass.getConstructor().newInstance();  
        m.invoke(obj, "刘德华");  
          
        System.out.println("***************获取私有的show4()方法******************");  
        m = stuClass.getDeclaredMethod("show4", int.class);  
        System.out.println(m);  
        m.setAccessible(true);//解除私有限定  
        Object result = m.invoke(obj, 20);//需要两个参数，一个是要调用的对象（获取有反射），一个是实参  
        System.out.println("返回值：" + result);  
    }
    
    public static void fields() throws Exception {
	//1.获取Class对象  
        Class stuClass = Class.forName("cn.liu.hui.peng.invoke.Student");  
        //2.获取字段  
        System.out.println("************获取所有公有的字段********************");  
        Field[] fieldArray = stuClass.getFields();  
        for(Field f : fieldArray){  
            System.out.println(f);  
        }  
        System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");  
        fieldArray = stuClass.getDeclaredFields();  
        for(Field f : fieldArray){  
            System.out.println(f);  
        }  
        System.out.println("*************获取公有字段**并调用***********************************");  
        Field f = stuClass.getField("name");  
        System.out.println(f);  
        //获取一个对象  
        Object obj = stuClass.newInstance();//产生Student对象--》Student stu = new Student();  
        //为字段设置值  
        f.set(obj, "刘德华");//为Student对象中的name属性赋值--》stu.name = "刘德华"  
        //验证  
        Student stu = (Student)obj;  
        System.out.println("验证姓名：" + stu.name);  
          
          
        System.out.println("**************获取私有字段****并调用********************************");  
        f = stuClass.getDeclaredField("phoneNum");  
        System.out.println(f);  
        f.setAccessible(true);//暴力反射，解除私有限定  
        f.set(obj, "18888889999");  
        System.out.println("验证电话：" + stu);  
    }

    
    public static void constructors() throws Exception {
	//1.加载Class对象  
        Class clazz = Class.forName("cn.liu.hui.peng.invoke.Student");  
          
          
        //2.获取所有公有构造方法  
        System.out.println("**********************所有公有构造方法*********************************");  
        Constructor[] conArray = clazz.getConstructors();  
        for(Constructor c : conArray){  
            System.out.println(c);  
        }  
          
          
        System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");  
        conArray = clazz.getDeclaredConstructors();  
        for(Constructor c : conArray){  
            System.out.println(c);  
        }  
          
        System.out.println("*****************获取公有、无参的构造方法*******************************");  
        Constructor con = clazz.getConstructor(null);  
        //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型  
        //2>、返回的是描述这个无参构造函数的类对象。  
      
        System.out.println("con = " + con);  
        //调用构造方法  
        Object obj = con.newInstance();  
    //  System.out.println("obj = " + obj);  
    //  Student stu = (Student)obj;  
          
        System.out.println("******************获取私有构造方法，并调用*******************************");  
        con = clazz.getDeclaredConstructor(int.class);  
        System.out.println(con);  
        //调用构造方法  
        con.setAccessible(true);//暴力访问(忽略掉访问修饰符)  
        obj = con.newInstance(100);  
    }
}
 