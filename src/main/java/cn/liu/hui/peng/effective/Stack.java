package cn.liu.hui.peng.effective; 

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 模拟
 * 只要类是自己管理内存，程序员就应该警惕内存泄露问题
 * @author	hz16092620 
 * @date	2018年7月13日 下午4:59:00
 * @version      
 */
public class Stack {
    
    public static void main(String[] args) {
	Stack stack = new Stack();
	for (int i = 0; i < 17; i++) {
	    stack.push(new Object());
        }
    }
    
    private Object[] elements;
    
    private int size = 0;
    
    private static final int DEFAULT_SIZE = 16;
    
    public Stack() {
	elements = new Object[DEFAULT_SIZE];
    }
    
    /**添加*/
    public void push(Object e) {
	ensureCapacity();
	elements[size++] = e;
    }
    
    /**获取最后一个元素*/
    public Object pop() {
	if (size == 0) {
	    throw new EmptyStackException();
	}
	Object obj = elements[--size];//程序控制解除引用
	elements[--size] = null;
	return obj;
    }

    /**这个Arrays复制涉及到底层的，到时候可以看看*/
    private void ensureCapacity() {
	int tempSize = elements.length;
	if (elements.length == size) {
	    elements = Arrays.copyOf(elements, 2 * size + 1);
	}
	if (elements.length > tempSize) {
	    System.out.println("扩容之后的大小：" + elements.length);
	}
    }
    
    
    
    
    
    
    
    
    
}
 