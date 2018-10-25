/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.spring.dao; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据访问异常层次体系
 * @author	hz16092620 
 * @date	2018年5月28日 下午3:12:55
 * @version      
 */
public class JdbcCustomerDao implements ICustomerDao {

    @Override
    public Object getById(Integer id) {
	
	SimpleDateFormat sf = new SimpleDateFormat();
	sf.format(new Date());
	try {
	    sf.parse("");
        } catch (ParseException e) {
            //异常层次体系，抛出具体类型的自定义的runtime异常，让sevice层知道什么错误。
	    throw new RuntimeException();
        }
	return null;
    }

}
 