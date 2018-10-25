/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.lang; 

/**
 * byte 工具类
 * @author	hz16092620 
 * @date	2018年8月24日 上午11:50:18
 * @version      
 */
public class ByteUtils {
    
    /**
     * byte 范围-128-127，有符号的byte转int会有负数出现，需要跳转过来。
     * */
    public static int byteToInt(Byte b) {
	int i = b;
	if (i < 0) {
	    return i + 256;
	} else {
	    return i;
	}
    }

}
 