package cn.liu.hui.peng.proxy.staticproxy; 

import cn.liu.hui.peng.proxy.HelloServiceProxy;
import cn.liu.hui.peng.proxy.UserService;
import cn.liu.hui.peng.proxy.UserServiceImpl;

/**
 * @author	hz16092620 
 * @date	2018年4月26日 上午10:50:05
 * @version      
 */
public class TestStaticProxy {
    
    
    public static void main(String[] args) {
	UserService service = new UserServiceImpl();
	UserService proxyService = new StaticHelloServiceProxy(service);
	System.out.println(proxyService.sayMorning("liuhp"));
    }

}
 