package cn.liu.hui.peng.proxy.cglib; 

import org.springframework.cglib.proxy.Enhancer;

/**
 * @author	hz16092620 
 * @date	2018年4月26日 上午11:48:02
 * @version      
 */
public class TestCglibProxy {
    
    public static void main(String[] args) {
	Enhancer en = new Enhancer();
	en.setSuperclass(Requestable.class);
	en.setCallback(new RequestCallback());
	
	Requestable re = (Requestable) en.create();
	re.request();
    }

}
 