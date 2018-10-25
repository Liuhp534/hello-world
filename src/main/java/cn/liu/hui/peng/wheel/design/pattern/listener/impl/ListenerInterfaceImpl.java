/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.wheel.design.pattern.listener.impl; 

import java.util.HashMap;
import java.util.Map;

import cn.liu.hui.peng.wheel.design.pattern.listener.Handler;
import cn.liu.hui.peng.wheel.design.pattern.listener.ListenerInterface;
import cn.liu.hui.peng.wheel.design.pattern.listener.entity.MessageEntity;

/**
 * <p>
 * 
 * 监听器实现类，根据不同类型的message调用不同的处理器解决。
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月20日 下午5:37:22
 * @version      
 */
public class ListenerInterfaceImpl implements ListenerInterface {
    
    private static Map<String, Handler> handlerMap =  new HashMap<>();
    
    /**
     * 配置处理器，一般通过spring配置，这里手动设置进去。or 通过注解扫描指定的注解配置
     * */
    static {
	handlerMap.put("one", new OneHandler());
	handlerMap.put("two", new TwoHandler());
	handlerMap.put("three", new ThreeHandler());
    }

    @Override
    public void message(MessageEntity message) {
	String type = message.getType();
	Handler handler = handlerMap.get(type);
	if (handler != null) {
	    handler.handler(message.getBody());
	}
    }
    
    
    public static void main(String[] args) {
	MessageEntity message = new MessageEntity();
	message.setBody("我是消息内容");
	message.setType("two");
	ListenerInterface listener = new ListenerInterfaceImpl();//由spring创建
	listener.message(message);
    }

}
 