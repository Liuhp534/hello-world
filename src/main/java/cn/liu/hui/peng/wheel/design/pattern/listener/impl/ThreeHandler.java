/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.wheel.design.pattern.listener.impl; 

import cn.liu.hui.peng.wheel.design.pattern.listener.Handler;


/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月20日 下午5:43:52
 * @version      
 */
public class ThreeHandler implements Handler {

    @Override
    public void handler(String param) {
	System.out.println("three handler param " + param);
    }

}
 