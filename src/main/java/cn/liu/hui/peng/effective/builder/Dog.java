/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.effective.builder; 

/**
 * 构建起构建对象
 * 
 * @author	hz16092620 
 * @date	2018年7月11日 上午11:39:10
 * @version      
 */
public class Dog {
    
    public static void main(String[] args) {
	//DogBuild test = new DogBuild.Builder().age(10).name("loge").height(10).sex(1).width(10).build();
	IBuilder<Dog> builder = new Builder("dogname");
	Dog obj = builder.build();
	System.out.println(obj.getName());
    }
    
    private String name;
    
    private int age;
    
    private int sex;
    
    private int height;
    
    private int width;
    
    private Dog(Builder builder) {
	this.name = builder.name;
	this.age = builder.age;
	this.sex = builder.sex;
	this.height = builder.height;
	this.width = builder.width;
	//在对象域中进行参数校验，在builder域校验，到对象域参数可能发生变化。
	//校验代码
    }
    /**
     * 静态内部类，这样可以不用依赖外部类实例化
     * */
    public static class Builder implements IBuilder<Dog> {

	private String name;

	private int age;

	private int sex;

	private int height;

	private int width;
	
	public Builder(String name) {//name为必选参数，其他的都是可选参数，可以设置一个默认的值。
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

	/**
	 * 创建对象
	 * */
	@Override
	public Dog build() {
	    return new Dog(this);
	}
    }
    
    //------------只有get方法----------------
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public int getSex() {
        return sex;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    
    

}
 