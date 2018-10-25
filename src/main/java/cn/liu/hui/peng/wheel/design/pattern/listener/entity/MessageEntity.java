/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.wheel.design.pattern.listener.entity; 

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月20日 下午5:50:26
 * @version      
 */
public class MessageEntity {
    
    /**
     * 消息类型
     * */
    private String type;
    
    /**
     * 消息主体
     * */
    private String body;

    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
    }

    
    public String getBody() {
        return body;
    }

    
    public void setBody(String body) {
        this.body = body;
    }
    
    
    

}
 