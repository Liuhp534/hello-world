/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.wheel.design.pattern.listener; 

import cn.liu.hui.peng.wheel.design.pattern.listener.entity.MessageEntity;

/**
 * <p>
 * 
 * 
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月20日 下午5:36:06
 * @version      
 */
public interface ListenerInterface {

    void message(MessageEntity message);
}
 