/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.thread.future; 

import java.util.concurrent.Callable;

/**
 * 找最大值
 * @author	hz16092620 
 * @date	2018年7月17日 下午4:44:00
 * @version      
 */
public class FindMax implements Callable<Integer> {
    
    private int start;
    
    private int end;
    
    private int[] data;
    
    public FindMax(int[] data, int start, int end) {
	this.data = data;
	this.start = start;
	this.end = end;
    }
    
    @Override
    public Integer call() throws Exception {
	int max = Integer.MIN_VALUE;
	for (int i = start; i < end; i++) {
	    if (data[i] > max) {
		max = data[i];
	    }
        }
	return max;
    }

}
 