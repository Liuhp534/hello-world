/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.effective.finalclose; 

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月31日 下午2:14:45
 * @version      
 */
public class ExtendAbstractClass extends AbstractClass {
    
    public static void main(String[] args) {
	AbstractClass obj = new ExtendAbstractClass();
	obj.close();
	obj.fun1();
    }

    @Override
    public void fun1() {
	System.out.println("i'm extends abstractClass");
    }

    public void close() {
	super.close();
	System.out.println("i'm extend close");
    }
}
 