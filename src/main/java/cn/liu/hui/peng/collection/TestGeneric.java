package cn.liu.hui.peng.collection; 

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月3日 下午5:57:29
 * @version      
 */
public class TestGeneric {
    
    public static void main(String[] args) {
	test1();
    }
    
    /**
     * 原生态的不安全，而？通配符的参数是安全的。
     * */
    static void test1() {
	List<?> lists = new ArrayList<>();
	//lists.add();//会报错
	List<String> list2 = new ArrayList<>();
	list2.add("hello");
	testListGeneric(list2);
    }
    
    
    static void testListGeneric(List<?> list) {
	if (list instanceof List<?>) {
	    List<?> listTemp = list;
	    for (Object obj : listTemp) {
		System.out.println(obj.toString());
	    }
	}
    }

}
 