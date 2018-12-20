package cn.liu.hui.peng.generic; 

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月14日 下午5:45:11
 * @version      
 */
public class Favorites {
    
    private Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
    
    public <T> void putFavorites(Class<T> c, T obj) {
	map.put(c, obj);
    }
    
    public<T>  T getFavorites(Class<T> type) {
	return type.cast(map.get(type));
    }

}
 