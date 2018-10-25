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

/**
 * @author	hz16092620 
 * @date	2018年4月23日 下午8:01:52
 * @version      
 */
public class UserServiceImpl implements UserService {

    @Override
    public String sayMorning(String name) {
	System.out.println("say hello....");
	return name;
    }

}
 