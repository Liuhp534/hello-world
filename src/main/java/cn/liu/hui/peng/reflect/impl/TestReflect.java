/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.reflect.impl; 

import cn.liu.hui.peng.reflect.ActionHandler;

/**
 * @author	hz16092620 
 * @date	2018年4月3日 下午4:03:05
 * @version      
 */
public class TestReflect {
    
    public static void main(String[] args) {
	test1();
    }
    
    public static void test1() {
	try {
	    ActionHandler ah = (ActionHandler) Class.forName("cn.liu.hui.peng.invoke.impl.ChinaActionHandler").newInstance();
	    ah.handler();
        } catch (InstantiationException e) {
	    e.printStackTrace();
        } catch (IllegalAccessException e) {
	    e.printStackTrace();
        } catch (ClassNotFoundException e) {
	    e.printStackTrace();
        }
    }

}
 