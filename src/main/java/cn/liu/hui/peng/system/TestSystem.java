/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.system; 

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author	hz16092620 
 * @date	2018年4月20日 下午2:38:00
 * @version      
 */
public class TestSystem {

    public static void main(String[] args) {
	m1();
    }
    
    /**
     * 查看系统的参数数据，比如：java.version 本地信息
     * */
    static void m1() {
	Properties properties = System.getProperties();
	Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();
	while (iterator.hasNext()) {
	    Entry<Object, Object> entry = iterator.next();
	    System.out.println(entry.getKey() + "===" + entry.getValue());
	}
    }
}
 