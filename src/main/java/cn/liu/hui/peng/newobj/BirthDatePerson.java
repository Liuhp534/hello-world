/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.newobj; 

import java.util.Calendar;
import java.util.Date;

import cn.liu.hui.peng.date.MathProgramTime;

/**
 * java effective第二章
 * @author	hz16092620 
 * @date	2018年7月11日 下午2:49:07
 * @version      
 */
public class BirthDatePerson {
    
    public static void main(String[] args) {
	MathProgramTime.start("计算时间");
	Calendar birthDate = Calendar.getInstance();
	birthDate.set(1965, 1, 2);
	BirthDatePerson person =  BirthDatePerson.newIntance();
	person.setBirthDate(birthDate.getTime());
	for (int i = 0; i < 10000000; i++) {
	    person.isBadyBoomer();
        }
	MathProgramTime.end();
	//计算时间 用时 : 7367毫秒！！！
	//重用计算时间 用时 : 5毫秒！！！
    }
    
    /**静态工厂方法*/
    public static BirthDatePerson newIntance() {
	return new BirthDatePerson();
    }
    
    private  Date birthDate;
    
    private static final Date startDate;
    
    private static final Date endDate;
    
    /**重用，不用每次调用方法在实例化时间的区间边界值*/
    static {
	Calendar start = Calendar.getInstance();
	start.set(1946, 1, 1);
	startDate = start.getTime();
	Calendar end = Calendar.getInstance();
	end.set(1966, 1, 1);
	endDate = end.getTime();
    }

    /**比较是否年龄在这个之间*/
    public  boolean isBadyBoomer() {
	boolean flag = (birthDate.compareTo(startDate) >= 0) && (birthDate.compareTo(endDate) <0);
	return flag;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
 