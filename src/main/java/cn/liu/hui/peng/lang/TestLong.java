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
 * @author	hz16092620 
 * @date	2018年5月3日 下午2:42:59
 * @version      
 */
public class TestLong {
    
    private Long a;
    
    public static void main(String[] args) {
	Integer a = 100;
	TestLong test = new TestLong();
	test.setA(a.longValue());
	System.out.println(test.getA());
	
	//数字有横线
	long l = 123_456;
	System.out.println(l);
    }

    
    public Long getA() {
        return a;
    }

    
    public void setA(Long a) {
        this.a = a;
    }
    
    

}
 