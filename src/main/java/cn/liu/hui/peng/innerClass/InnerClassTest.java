package cn.liu.hui.peng.innerClass; 

/**
 * <p>
 * 
 *内部类和外部类是处于同一个级别的,内部类不会因为定义在方法中就会随着方法的执行完毕就被销毁；
 *所以为了防止方法执行完毕，引用了一个不存在的变量，就copy一份局部变量作为内部类的成员变量，
 *当局部变量没有之后，实际访问的是copy的复制品.
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月18日 下午4:16:23
 * @version      
 */
public class InnerClassTest {
    
    public static void main(String[] args) {
	InnerClassTest i = new InnerClassTest();
	i.fun(100);
	System.out.println("方法执行完成之后，局部变量作用域终止了");
	obj.printA();
	i.fun1(100);
    }
    
    public static InnerInterface obj = null;
    
    /**
     * 局部变量a作用域只能是方法内部，但是匿名内部类不会随着方法执行完毕而被销毁，
     * 就copy了一份局部变量，同时为了保证一致性，设置为final。
     * */
    public void fun(final int a) {
	obj = new InnerInterface() {

	    @Override
	    public void printA() {
		System.out.println("I'm final param " + a);
	    }
	    
	};
    }
    
    /**
     * 同样拷贝一份作为InnerClass的成员变量
     * */
    public void fun1(final int b) {
	class InnerClass {
	    public void printB() {
		System.out.println("I'm final param " + b);
	    }
	}
	new InnerClass().printB();
    }

}
 