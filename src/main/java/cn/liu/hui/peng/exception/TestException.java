package cn.liu.hui.peng.exception; 

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author	hz16092620 
 * @date	2018年4月3日 下午2:24:35
 * @version      
 */
public class TestException {
    
    public static void main(String[] args) {
	//test();
	testTry();
    }
    
    /**
     * throw 就是抛出异常，不会执行后面的。不受检查异常，用了throw运行时异常。
     * */
    public static void test() {
	String str = null;
	    str.split(",");
	System.out.println("after!!!");
	//throw new RuntimeException(e.getMessage());
    }
    
    /**
     * 会执行到下面，只是扑捉了，没有throw。
     * */
    public static void testTry() {
	try {
	    InputStream io = new FileInputStream("11");
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	System.out.println("after!!!");
    }

}
 