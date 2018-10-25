/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.excel.zhongyi; 

import java.util.List;

/**
 * <p>
 * 
 * 封装执行的sql
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月11日 下午4:56:12
 * @version      
 */
public class ZhongYiDTO {
    
    private String transNo;
    
    private String insurantId;
    
    private int platform = 2;//1-主站，2-齐欣，3-聚米
    
    private String key;//删除的条件值
    
    private List<String> relationSql;//is库sql语句
    
    private List<String> policySql;//policy库sql语句

    
    public String getTransNo() {
        return transNo;
    }

    
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    
    public String getInsurantId() {
        return insurantId;
    }

    
    public void setInsurantId(String insurantId) {
        this.insurantId = insurantId;
    }

    
    public int getPlatform() {
        return platform;
    }

    
    public void setPlatform(int platform) {
        this.platform = platform;
    }

    
    public List<String> getRelationSql() {
        return relationSql;
    }

    
    public void setRelationSql(List<String> relationSql) {
        this.relationSql = relationSql;
    }

    
    public List<String> getPolicySql() {
        return policySql;
    }

    
    public void setPolicySql(List<String> policySql) {
        this.policySql = policySql;
    }


    
    public String getKey() {
        return key;
    }


    
    public void setKey(String key) {
        this.key = key;
    }
    
    

}
 