/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.wheel; 

import java.util.UUID;

/**
 * <p>
 * 
 * uuid工具类
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月14日 下午5:13:50
 * @version      
 */
public class UuidUtils {

    /**
     * 获取字符串格式的uuid
     * */
    public static String getUuid() {
	return UUID.randomUUID().toString();
    }
}
 