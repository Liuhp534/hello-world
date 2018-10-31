/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.wheel.design.singleton; 

/**
 * <p>
 * 
 * 单例实现：构造器私有、公有的静态final域or公有的静态方法
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月31日 上午10:56:26
 * @version      
 */
public class SingletonOne {

    /**
     * 公有的静态final域
     * */
    public static final SingletonOne INSTANCE = new SingletonOne();
    
    /**
     * 构造器私有
     * */
    private SingletonOne() {
	
    }
    
    public static void main(String[] args) {
	System.out.println(SingletonOne.INSTANCE);
    }
    
}
 