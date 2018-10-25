/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.generic; 

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月14日 下午5:44:04
 * @version      
 */
public class TestGeneric {

    
    public static void main(String[] args) {
	Favorites f = new Favorites();
	f.putFavorites(String.class, "aaaa");
	f.putFavorites(String.class, "bbb");
	
	System.out.println(f.getFavorites(String.class));
    }
    
    static void fun() {
	
    }
}
 