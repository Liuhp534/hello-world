/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.ioc.setter; 

/**
 * @author	hz16092620 
 * @date	2018年3月29日 上午11:48:38
 * @version      
 */
public class PeopleDayNewsListener implements CommonNewsListener {

    @Override
    public void getNews() {
	System.out.println("获取人民日报新闻。。。。");
    }

}
 