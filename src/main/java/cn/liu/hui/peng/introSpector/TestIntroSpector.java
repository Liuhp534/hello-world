package cn.liu.hui.peng.introSpector; 

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author	hz16092620 
 * @date	2018年4月4日 下午2:00:13
 * @version      
 */
public class TestIntroSpector {
    
    private static final User user;
    
    static {
	user = new User();
	user.setName("张三") ;
        user.setAge(21) ;
        user.setGender("1") ;
        user.setBirthday(new Date()) ;
        user.setAddress("北京丰台") ;
    }
    
    public static void main(String[] args) throws Exception {
	//testWrite(user);
	testRead(user);
    }
    
    public static void testWrite(User user) throws Exception {
	PropertyDescriptor pd = new PropertyDescriptor("name", User.class);
	Method method = pd.getWriteMethod();
	method.invoke(user, "内省设置名称");
    }

    public static void testRead(User user) throws Exception {
	BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
	PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors() ;
	MethodDescriptor[] mds = beanInfo.getMethodDescriptors();
	for (int i = 0; i < mds.length; i++) {
	    String methodName = mds[i].getName();
	    Method method = mds[i].getMethod();
	    System.out.println(methodName);
            //Object result = mds[i].getMethod().invoke(user) ;
            //System.out.println(methodName + "-->" + result);
        }
        for (PropertyDescriptor pd : pds) {
            Method method = pd.getReadMethod() ;
            String methodName = method.getName() ;
            Object result = method.invoke(user) ;
            System.out.println(methodName + "-->" + result);
        }
    }
    
    public static void testUtils() {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("name", "工具设置名称");
	map.put("age", 100);
	map.put("address", "湖南株洲");
	MyBeanUtils.populate(user, map);
	
	Object result = MyBeanUtils.getPropertyValue("name", user);
	System.out.println(result);
	Object result1 = MyBeanUtils.getPropertyValue("age", user);
	System.out.println(result1);
	Object result2 = MyBeanUtils.getPropertyValue("address", user);
	System.out.println(result2);
    }
}












 