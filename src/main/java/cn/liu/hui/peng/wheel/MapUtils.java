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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <p>
 * 
 * map相关的使用
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月12日 上午9:50:30
 * @version      
 */
public class MapUtils {
    
    public static void main(String[] args) {
	forEarch();
    }
    
    /**
     * 遍历map的例子
     * */
    static void forEarch() {
	Map<String, String> map = new HashMap<>();
	map.put("1", "aaa");
	map.put("2", "bbb");
	map.put("3", "ccc");
	//1、传统keySet 遍历key
	Set<String> keys = map.keySet();
	for (Iterator<String> it = keys.iterator(); it.hasNext();) {
	    String key = (String) it.next();
	    String value = map.get(key);
	    System.out.println("1 key = " + key + " value = " + value);
	}
	//2、传统values 遍历
	Collection<String> values = map.values();
	for (Iterator<String> it = values.iterator(); it.hasNext();) {
	    String value = (String) it.next();
	    System.out.println("2 value = " + value);
	}
	//3、增强的foreach
	for (Map.Entry<String, String> entry : map.entrySet()) {
	    String key = entry.getKey();
	    String value = entry.getValue();
	    System.out.println("3 key = " + key + " value = " + value);
	}
	//4、增强的foreach
	for (String key : map.keySet()) {
	    String value = map.get(key);
	    System.out.println("4 key = " + key + " value = " + value);
	}
    }

}
 