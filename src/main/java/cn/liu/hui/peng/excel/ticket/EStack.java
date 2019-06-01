package cn.liu.hui.peng.excel.ticket;

import java.util.*;

/**
 * @description: 泛型栈
 * @author: hz16092620
 * @create: 2019-05-20 14:18
 */
public class EStack<E> {

    /*测试栈*/
    public static void main(String[] args) {
        test2();
    }

    /*泛型可以支持子类*/
    private static void test4() {
        EStack<Number> eStack = new EStack<>();
        eStack.push(new Integer(1));
        Collection<Object> c = new ArrayList<>();
        eStack.popAll(c);//consumer 需要使用super
    }

    /*泛型可以支持子类*/
    private static void test3() {
        EStack<Number> eStack = new EStack<>();
        eStack.push(new Integer(1));
        Iterable<Integer> integers = new ArrayList<>();
        /*
        * public void pushAll(Iterable<E> it) {
        for (E e : it) {
            this.push(e);
        }
    }
        * */
        //eStack.pushAll(integers);这样不行的
        eStack.pushAll(integers);//这样就ok了
        while (!eStack.isEmpty()) {
            System.out.println(eStack.pop());
        }
    }

    /*泛型可以支持子类*/
    private static void test2() {
        EStack<List> eStack = new EStack<>();
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        eStack.push(list1);
        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(2);
        eStack.push(list2);
        while (!eStack.isEmpty()) {
            System.out.println(eStack.pop());
        }
    }

    /*测试出栈和入栈*/
    private static void test1() {
        EStack<String> eStack = new EStack<>();
        for (int i = 0; i < 50; i++) {
            eStack.push(i + "");
        }
        while (!eStack.isEmpty()) {
            System.out.println(eStack.pop());
        }
    }

    private static final int default_capacity = 16;

    private int size;

    //private E[] elements = null;
    private Object[] elements = null;

    public EStack () {
        /*
         * 数组不能创建位置类型的 new E[default_capacity]
         * 方法一：E[] elements = (E[]) new Object[default_capacity]; 通过类型转换
         * 方法二：Object[] elements = new Object[default_capacity];
         * 这个时候E e = (E) this.elements[--size];需要转换 ArrayList用的是这种
         * */
        //elements = (E[]) new Object[default_capacity];
        elements = new Object[default_capacity];
    }

    public boolean push(E e) {
        ensureCapacity();
        elements[size++] = e;
        return true;
    }

    public void pushAll(Iterable<? extends E> it) {
        for (E e : it) {
            this.push(e);
        }
    }

    public void popAll(Collection<? super E> c) {
        for (int i = 0; i < elements.length; i ++) {
            c.add(this.pop());
        }
    }

    public void ensureCapacity() {
        if (size >= this.elements.length) {
            int oldCapacity = this.elements.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            System.out.println(String.format("扩容 oldCapacity %d newCapacity %d 增量 %d", oldCapacity, newCapacity, (newCapacity - oldCapacity)));
            this.elements = Arrays.copyOf(this.elements, newCapacity);
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public E pop() {
        if (size <= 0) {
            throw new RuntimeException();
        }
        E e = (E) this.elements[--size];
        this.elements[size] = null;
        return e;
    }

    @Override
    public String toString() {
        if (this.elements == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Object temp = null;
        for (int i = 0; i < size; i ++) {
            temp = this.elements[i];
            sb.append(temp.toString().length() == 1 ? ("0" + temp.toString()) : temp.toString());
            if (i == (size - 1)) {
                sb.append("]");
            } else {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public Set<String> getH() {
        Set<String> temp = new LinkedHashSet<>();
        for (int i = 0; i < size; i ++) {
            temp.add(this.elements[i].toString().length() == 1 ? ("0" + this.elements[i].toString()) : this.elements[i].toString());
        }
        return temp;
    }

    public Set<String> getT(Set<String> allSet) {
        Set<String> temp = new LinkedHashSet<>();
        Set<String> hSet = this.getH();
        for (String str : allSet) {
            if (!hSet.contains(str)) {
                temp.add(str);
            }
        }
        return temp;
    }
}
