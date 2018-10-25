/**

 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.url; 

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author	hz16092620 
 * @date	2018年3月11日 上午8:51:17
 * @version      
 */
public class TestURL {
    
    public static void main(String[] args) throws IOException {
	//testUrlMethod();
	//testToString();
	//testEncode();
	testUncode();
    }
    
    public static void testUrlMethod() throws IOException {
	URL url = new URL("https://www.oreilly.com/books/index.html");
	System.out.println("userInfo : " + url.getUserInfo());
	System.out.println("authority : " + url.getAuthority());
	System.out.println("content : " + url.getContent());
	System.out.println("port : " + url.getPort());
	System.out.println("default port : " + url.getDefaultPort());
	System.out.println("Protocol : " + url.getProtocol());
	System.out.println("Path : " + url.getPath());
    }
    
    public static void testUrlIo() {
	try {
	    URL url = new URL("https://www.oreilly.com");
	    InputStream in = url.openStream();
	    int c;
	    while((c = in.read()) != -1) {
		System.out.print((char)c);
	    }
        } catch (MalformedURLException e) {
	    e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
        }
    }
    
    /**
     * toString 和 toExternalForm
     * */
    static void testToString() throws MalformedURLException {
	URL url = new URL("http://www.baidu.com");
	System.out.println(url.toString());
	System.out.println(url.toExternalForm());
    }
    
    static void testEncode() throws UnsupportedEncodingException {
	String s = "this is apple";
	System.out.println(URLEncoder.encode(s, "utf-8"));
	s = "this*is*apple";
	System.out.println(URLEncoder.encode(s, "utf-8"));
    }
    
    static void testUncode() throws UnsupportedEncodingException {
	String s = "this+is+apple";
	System.out.println(URLDecoder.decode(s, "utf-8"));
    }
    
    
    
    
    
    
}
 