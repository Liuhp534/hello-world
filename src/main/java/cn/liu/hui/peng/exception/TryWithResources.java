/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.exception; 

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author	hz16092620 
 * @date	2018年5月31日 上午11:27:41
 * @version      
 */
public class TryWithResources {
    
    public static void main(String[] args) {
	//test();
	test2();
    }
    
    /**
     * 1.7新加入的的资源管理
     * */
    private static void test2() {
	try (InputStream in = new FileInputStream("E:/201804_work/交接.txt")) {
	    int read = in.read();
	    while (read != -1) {
		System.out.println((char)read);
		read = in.read();
	    }
	} catch (IOException e ) {
	    e.printStackTrace();
        }
    }
    
    /**
     * 一般的资源管理
     * */
    private static void test() {
	InputStream in = null;
	try {
	    in = new FileInputStream("E:/201804_work/交接.txt");
	    int read = in.read();
	    while (read != -1) {
		System.out.println((char)read);
		read = in.read();
	    }
        } catch (FileNotFoundException e) {
	    e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
        } finally {
            if (in != null) {
        	try {
	            in.close();
                } catch (IOException e) {
	            //日志输出
                }
            }
        }
    }

}
 