/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.interface_coustom; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 
 *测试泛型方法
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月5日 下午2:23:12
 * @version      
 */
public class TestGenericMethed {

    
    public static void main(String[] args) {
	
	/*Set<String> set1 = new LinkedHashSet<String>(Arrays.asList("one", "two", "three"));
	Set<String> set2 = new LinkedHashSet<String>(Arrays.asList("1", "2", "3"));
	System.out.println(union(set1, set2));*/
	
	/*List<Dog> list = new ArrayList<>();
	list.add(new Dog(10));
	list.add(new Dog(2));
	list.add(new Dog(5));
	list.add(new Dog(1));
	System.out.println(max(list));*/
	
	List<Integer> integers = new ArrayList<>(Arrays.asList(1,2,3,4));
	List<Double> doubles = new ArrayList<>(Arrays.asList(1.1,2.2,3.3,4.4));
	System.out.println(genericUnion(integers, doubles));
    }
    
    /**
     * 计算最大值
     * */
    static <T extends Comparable<T>> T max(List<T> list) {
	T max = null;
	if (null != list && list.size() > 0) {
	    for (int i = 0; i < (list.size() -1); i++) {
		if (max == null) {
		    max = list.get(i);
		} else {
		    if (max.compareTo(list.get(i+1)) < 0) {
			max = list.get(i + 1);
		    }
		}
	    }
	}
	return max;
    }
    
    /**
     * 测试类，内部类，当不需要直线外部对象的时候，使用静态内部类。
     * */
    static class Dog implements Comparable<Dog>{
	
	int age;
	
	Dog(int age) {
	    this.age = age;
	}

	@Override
	public int compareTo(Dog o) {
	    if (this.age > o.getAge()) {
		return 1;
	    } else if (this.age == o.getAge()) {
		return 0;
	    }
	    return -1;
	}
	
	public int getAge() {
	    return age;
	}

	
	public void setAge(int age) {
	    this.age = age;
	}

	@Override
	public String toString() {
	    return "Dog [age=" + age + "]";
	}
	
    }
    
    /**
     * 合并集合set
     * */
    static <E> Set<E> union(Set<E> set1, Set<E> set2) {
	Set<E> set = new LinkedHashSet<E>(set1);
	set.addAll(set2);
	return set;
    }
    
    /**
     * 合并集合list，通配符
     * */
    static <E> List<E> genericUnion(List<? extends E> list1, List<? extends E> list2) {
	List<E> list = new LinkedList<E>(list1);
	list.addAll(list2);
	return list;
    }
    
    /**
     * 交换位置
     * */
    static void swap(List<?> list, int i, int j) {
	swapHelpler(list, i, j);//通配符的不能直接进行操作，需要转换下。
    }
    
    static <E> void swapHelpler(List<E> list, int i, int j) {
	list.set(i, list.set(j, list.get(i)));
    }
}
 