package cn.liu.hui.peng.thread;

/**
 * @author hz16092620
 * @date 2018年4月12日 上午11:32:06
 */
public class TestBaseThread {

    public static void main(String[] args) {
        testThreadLocal();
    }

    /**
     * ThreadLocal用空间换取时间（一个线程一个副本），线程安全变量。
     */
    public static void testThreadLocal() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("good");
        System.out.println(threadLocal.get());
        threadLocal.remove();
        System.out.println(threadLocal.get());
    }

}
 