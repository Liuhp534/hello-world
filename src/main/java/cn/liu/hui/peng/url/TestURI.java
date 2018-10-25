/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.url; 

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author	hz16092620 
 * @date	2018年8月24日 下午3:14:38
 * @version      
 */
public class TestURI {
    
    public static void main(String[] args) throws URISyntaxException {
	testCreate();
    }
    
    
    static void testCreate() throws URISyntaxException {
	URI uri = new URI(null, "www.baidu.com",null);
	System.out.println(uri);
    }
    

}
 