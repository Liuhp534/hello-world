/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.effective.compare; 

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * <p>
 * 
 * TreeSet 和 HashSet添加元素（BigDecimal）的方式不同。
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月30日 下午7:46:57
 * @version      
 */
public class ComparableTest {
    
    public static void main(String[] args) {
	BigDecimal b1 = new BigDecimal("1.0");
	BigDecimal b2 = new BigDecimal("1.00");
	
	TreeSet<BigDecimal> set1 = new TreeSet<>();
	set1.add(b1);
	set1.add(b2);
	HashSet<BigDecimal> set2 = new HashSet<>();
	set2.add(b1);
	set2.add(b2);
	
	System.out.println("b1 equals b2 : " + b1.equals(b2));
	System.out.println("b1 compareTo b2 : " + b1.compareTo(b2));
	System.out.println("set1.size " + set1.size());
	System.out.println("set2.size " + set2.size());
    }

}
 