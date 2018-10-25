/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.introSpector; 

import java.util.Date;

/**
 * @author	hz16092620 
 * @date	2018年4月4日 下午1:58:53
 * @version      
 */
public class User {
    
    private String name;
    
    private Integer age;
    
    private String gender;
    
    private Date birthday;
    
    private String address;

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public Integer getAge() {
        return age;
    }

    
    public void setAge(Integer age) {
        this.age = age;
    }

    
    public String getGender() {
        return gender;
    }

    
    public void setGender(String gender) {
        this.gender = gender;
    }

    
    public Date getBirthday() {
        return birthday;
    }

    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }
    
    

}
 