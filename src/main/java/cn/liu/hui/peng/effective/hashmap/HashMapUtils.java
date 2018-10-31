package cn.liu.hui.peng.effective.hashmap; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * HashMap工具类
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月31日 上午9:42:56
 * @version      
 */
public class HashMapUtils {
    
    public static void main(String[] args) {
	Map<String, List<String>> map = HashMapUtils.newInstance();
	Map<String, List<String>> map1 = new HashMap<>();//类型推导1.7
    }
    
    
    /**
     * <p>
     * 
     * 创建参数化类型实例，代码更加简洁
     *
     * </p>
     * @return
     *  
     * @author	hz16092620 
     * @date	2018年10月31日 上午9:47:49
     * @version      
     */ 
    public static <K, V> HashMap<K, V> newInstance() {
	return new HashMap<K, V>();
    }

}
 