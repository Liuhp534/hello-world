package cn.liu.hui.peng.annotation_custom; 

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 
 *@Retention 扣留
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月6日 下午7:53:14
 * @version      
 */
@Retention(RetentionPolicy.RUNTIME)//作用时期
@Target(ElementType.METHOD)//作用域
public @interface ExceptionTest {

    Class<? extends Exception>[] value();//用于限制拦截哪些值
}
 