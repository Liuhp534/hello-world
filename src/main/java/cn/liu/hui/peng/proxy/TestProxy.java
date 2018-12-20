package cn.liu.hui.peng.proxy; 

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author	hz16092620 
 * @date	2018年4月23日 下午7:54:31
 * @version      
 */
public class TestProxy {

    public static void main(String[] args) {
	UserService service = new UserServiceImpl();
	//实现特定业务的代理类
	InvocationHandler proxy = new HelloServiceProxy(service);
	//参数和具体的实现类对象没有关系，第二个参数UserService.class.getInterfaces()获取的是接口的接口，不要理错了。
	service = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[] {UserService.class}, proxy);
	service.sayMorning("aaa");
    }
    
    
}
 