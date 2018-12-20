package cn.liu.hui.peng.effective.finalclose; 

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月31日 下午2:13:27
 * @version      
 */
public abstract class AbstractClass {
    
    public abstract void fun1();
    
    public void close() {
	System.out.println("i'm close.");
    }

}
 