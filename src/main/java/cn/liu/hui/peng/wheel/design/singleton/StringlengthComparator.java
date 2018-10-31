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

import java.util.Comparator;

/**
 * <p>
 * 无状态的，没有域
 * 单例
 * 函数指针主要是实现策略模式
 * </p>
 * @author	hz16092620 
 * @date	2018年10月25日 下午5:52:45
 * @version      
 */
public class StringlengthComparator implements Comparator<String> {
    
    private static final StringlengthComparator INSTANCE = new StringlengthComparator();//也可以缓存

    private StringlengthComparator () {
	
    }

    @Override
    public int compare(String o1, String o2) {
	return o1.toString().length() - o2.toString().length();
    }
    
    public static StringlengthComparator getInstance() {
	return INSTANCE;
    }
    
}
 