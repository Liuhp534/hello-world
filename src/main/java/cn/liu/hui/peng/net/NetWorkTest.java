package cn.liu.hui.peng.net; 

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import cn.liu.hui.peng.lang.ByteUtils;

/**
 * @author	hz16092620 
 * @date	2018年8月24日 上午11:17:18
 * @version      
 */
public class NetWorkTest {
    
    public static void main(String[] args) throws SocketException {
	//test1();
	testSpammer("207.34.56.23");
    }
    
    /**
     * name:lo (Software Loopback Interface 1)----------start
        /127.0.0.1
        /0:0:0:0:0:0:0:1
        name:lo (Software Loopback Interface 1)----------end
        
        name:eth3 (Realtek PCIe GBE Family Controller)----------start
        /192.168.11.76
        /fe80:0:0:0:2833:a913:ecbd:e646%10
        name:eth3 (Realtek PCIe GBE Family Controller)----------end
     * 列出所有网络接口
     * */
    static void test1() throws SocketException {
	Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	NetworkInterface networkInterface = null;
	while (interfaces.hasMoreElements()) {
	    networkInterface = interfaces.nextElement();
	    System.out.println(networkInterface + "----------start");
	    
	    Enumeration<InetAddress> nets = networkInterface.getInetAddresses();
	    while (nets.hasMoreElements()) {
		System.out.println(nets.nextElement());
	    }
	    System.out.println(networkInterface + "----------end");
	}
	
    }
    
    /**
     * 是否是非黑名单
     * */
    static void testSpammer(String org) {
	String blackhole = "sbl.spamhaus.org";
	String ip = "";
	
	try {
	    InetAddress addr = InetAddress.getByName(org);
	    byte[] bs = addr.getAddress();
	    for (int i = 0; i < bs.length; i++) {
		ip += ByteUtils.byteToInt(bs[i]) + ".";
            }
	    ip += blackhole;
	    InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
	    e.printStackTrace();
        }
    }

}
 