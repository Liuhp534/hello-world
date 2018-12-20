package cn.liu.hui.peng.url; 

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author	hz16092620 
 * @date	2018年8月24日 下午3:14:38
 * @version      
 */
public class TestURI {
    
    public static void main(String[] args) throws URISyntaxException {
	testCreate();
    }
    
    
    static void testCreate() throws URISyntaxException {
	URI uri = new URI(null, "www.baidu.com",null);
	System.out.println(uri);
    }
    

}
 