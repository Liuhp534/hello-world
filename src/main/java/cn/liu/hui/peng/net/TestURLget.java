package cn.liu.hui.peng.net; 

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * url的get请求
 * @author	hz16092620 
 * @date	2018年8月27日 上午11:07:36
 * @version      
 */
public class TestURLget {

    
    public static void main(String[] args) throws IOException {
	//urlGet();
	urlConnectionGet();
    }
    
    
    /**
     * url直接获取
     * */
    static void urlGet() throws MalformedURLException {
	String urlStr = "http://www.jumi18.com/n/product/list?keyWords=健康";
	URL url =  new URL(urlStr);
	try (InputStream in = new BufferedInputStream(url.openStream())) {
	    InputStreamReader read = new InputStreamReader(in);
	    int c = -1;
	    while ( (c = read.read()) != -1) {
		System.out.print((char)c);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
        }
    }
    
    /**
     * urlconnection的方式
     * */
    static void urlConnectionGet() throws IOException {
	String urlStr = "http://www.jumi18.com/n/product/list?keyWords=健康";
	URL url =  new URL(urlStr);
	URLConnection conn = url.openConnection();
	//content-type
	String contentType = conn.getContentType();
	System.out.println("contentType : " + contentType);
	//content-length
	int length = conn.getContentLength();
	System.out.println("length : " + length);
	//expires 为0表示不过期
	long expires = conn.getExpiration();
	System.out.println("expires : " + expires);
	InputStream in = conn.getInputStream();
	BufferedInputStream buffer = new BufferedInputStream(in);
	InputStreamReader reader = new InputStreamReader(buffer);
	int c = -1;
	/*while ((c = reader.read()) != -1) {
	    System.out.print((char) c);
	}*/
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
 