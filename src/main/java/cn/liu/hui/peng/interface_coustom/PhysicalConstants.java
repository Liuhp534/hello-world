/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.interface_coustom; 

/**
 * <p>
 * 
 *静态域可以使用不能实例化的工具类
 *接口只用来定义类型，不能用于定义常量接口，只有静态final域；
 * </p>
 * @author	hz16092620 
 * @date	2018年9月3日 上午10:39:29
 * @version      
 */
public class PhysicalConstants {
    
    /**可以使用不能实例化的工具类*/
    private PhysicalConstants() {
	
    }

    static final double AVOGADROS_NUMBER = 6.02222;
}
 