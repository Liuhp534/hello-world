/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.net; 

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author	hz16092620 
 * @date	2018年7月30日 下午7:29:37
 * @version      
 */
public class NetTest {
    
    public static void main(String[] args) throws UnknownHostException {
	//inetGetByName();
	//inetGetByIP();
	//inetGetAllByName();
	//inetGetLocalHost();
	inetEqual();
    }
    
    /**根据主机名获取ip*/
    static void inetGetByName() {
	try {
	    InetAddress addr = InetAddress.getByName("www.baidu.com");
	    System.out.println(addr.getHostAddress());
        } catch (UnknownHostException e) {
	    e.printStackTrace();
        }
    }
    
    /**根据主机名获取ip*/
    static void inetGetAllByName() {
	try {
	    InetAddress[] addrs = InetAddress.getAllByName("www.jd.com");
	    for (int i = 0; i < addrs.length; i++) {
	        System.out.println(addrs[i]);
            }
        } catch (UnknownHostException e) {
	    e.printStackTrace();
        }
    }

    /**根据ip获取主机名*/
    static void inetGetByIP() {
	try {
	    InetAddress addr = InetAddress.getByName("202.104.111.51");
	    System.out.println(addr.getCanonicalHostName());
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }
    
    /**根据本地主机名*/
    static void inetGetLocalHost() {
	try {
	    InetAddress addr = InetAddress.getLocalHost();
	    System.out.println(addr);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }
    
    /**测试equels 和 hashCode*/
    static void inetEqual() throws UnknownHostException {
	byte[] b1 = {(byte) 202,104,111,51};
	InetAddress a = InetAddress.getByAddress("www.jd.com", b1);
	InetAddress b = InetAddress.getByAddress("www.baidu.com", b1);
	if (a.equals(b)) {
	    System.out.println("a equals b");
	}
	if (a == b) {
	    System.out.println("a == b");
	}
	if (a.hashCode() == b.hashCode()) {
	    System.out.println("a.hashCod == b.hashcode");
	}
    }


}
 