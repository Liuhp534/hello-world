package cn.liu.hui.peng.system; 

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author	hz16092620 
 * @date	2018年4月20日 下午2:38:00
 * @version      
 */
public class TestSystem {

    public static void main(String[] args) {
	m1();
    }
    
    /**
     * 查看系统的参数数据，比如：java.version 本地信息
     * */
    static void m1() {
	Properties properties = System.getProperties();
	Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();
	while (iterator.hasNext()) {
	    Entry<Object, Object> entry = iterator.next();
	    System.out.println(entry.getKey() + "===" + entry.getValue());
	}
    }
}
 