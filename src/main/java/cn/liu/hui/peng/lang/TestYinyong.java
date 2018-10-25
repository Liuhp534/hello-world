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
 * @date	2018年5月14日 下午5:00:40
 * @version      
 */
public class TestYinyong {
    
    /**
     * 除了复杂类型的，其他的类型的基本上改变不了对象里面的值。
     * */
    public static void main(String[] args) {
	Dog dog = new Dog();
	dog.setAge(1000);
	dog.setSize(1);
	dog.setName("aaa");
	
	Integer age = dog.getAge();
	age.valueOf(8888);
	System.out.println(age);
	System.out.println(new Integer(1).valueOf(2000));
	
	//System.out.println(dog.getAge());
	int a;
	int b = 0;
	//a = a + b;//没有初始化的局部变量会报错
	b++;
	System.out.println(b);
    }
    
    
    
    static class Dog {
	private String name;
	
	private Integer age;
	
	private int size;

	
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

	
        public int getSize() {
            return size;
        }

	
        public void setSize(int size) {
            this.size = size;
        }
	
	
    }

}
 