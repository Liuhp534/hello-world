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

/**
 * @author	hz16092620 
 * @date	2018年7月11日 上午11:39:10
 * @version      
 */
public class DogBuild {
    
    public static void main(String[] args) {
	//DogBuild test = new DogBuild.Builder().age(10).name("loge").height(10).sex(1).width(10).build();
    }
    
    private String name;
    
    private int age;
    
    private int sex;
    
    private int height;
    
    private int width;
    
    private DogBuild(String name, int age, int sex, int height, int width) {
	this.name = name;
	this.age = age;
	this.sex = sex;
	this.height = height;
	this.width = width;
    }
    
    public static class Builder {

	private String name;

	private int age;

	private int sex;

	private int height;

	private int width;
	
	public Builder() {
	    
	}
	
	public Builder(String name) {
	    this.name = name;
	}
	
	public Builder name(String name) {
	    this.name = name;
	    return this;
	}
	
	public Builder age(int age) {
	    this.age = age;
	    return this;
	}
	
	public Builder sex(int sex) {
	    this.sex = sex;
	    return this;
	}
	
	public Builder height(int height) {
	    this.height = height;
	    return this;
	}
	
	public Builder width(int width) {
	    this.width = width;
	    return this;
	}
	
	public DogBuild build() {
	    return new DogBuild(name, age, sex, height, width);
	}
    }

}
 