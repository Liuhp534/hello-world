package cn.liu.hui.peng.thread; 

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author	hz16092620 
 * @date	2018年4月13日 下午7:15:04
 * @version      
 */
public class TestExecutorService {
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
	//testNewCacheThreadPool();
	//testSingleThreadExecutor();
	//testSingleThreadScheduledExecutor();
	testFutureThread();
    }
    
    /**
     * 根据需要可以创建新线程的线程池。线程池中曾经创建的线程，在完成某个任务后也许会被用来完成另外一项任务。
     * */
    static void testNewCacheThreadPool() {
	ExecutorService exec = Executors.newCachedThreadPool();
	for (int i = 0; i < 50; i++) {
	    exec.submit(new Callable<Integer>() {

		@Override
		public Integer call() throws Exception {
		    //Thread.sleep(100L);
		    System.out.println(Thread.currentThread().getName() + " doning task.....");
		    return null;
		}
	    });
	}
	exec.shutdown();
    }

    /**
     * 创建一个使用单个 worker 线程的 Executor。即使任务再多，也只用1个线程完成任务。
     * */
    static void testSingleThreadExecutor() {
	ExecutorService exec = Executors.newSingleThreadExecutor();
	for (int i = 0; i < 5; i++) {
	    exec.execute(new Runnable() {//execute方法接收Runnable对象，无返回值
	        @Override
	        public void run() {
	            System.out.println(Thread.currentThread().getName() + " doning task.....");
	        }
	    });
	}
	exec.shutdown();
    }
    
    /**
     * 创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期执行。???
     * */
    static void testSingleThreadScheduledExecutor() {
	ExecutorService exec = Executors.newSingleThreadScheduledExecutor();
	for (int i = 0; i < 5; i++) {
	    exec.execute(new Runnable() {//execute方法接收Runnable对象，无返回值
	        @Override
	        public void run() {
	            System.out.println(Thread.currentThread().getName() + " doning task.....");
	        }
	    });
	}
	exec.shutdown();
    }
    
    /**
     * 创建一个有返回值的线程。
     * @throws ExecutionException 
     * @throws InterruptedException 
     * */
    static void testFutureThread() throws InterruptedException, ExecutionException {
	ExecutorService exec = Executors.newFixedThreadPool(10);
	List<FutureTask<String>> list = new ArrayList<FutureTask<String>>();
	for (int i = 0; i < 10; i++) {
	    FutureTask<String> future = (FutureTask<String>) exec.submit(new TestCallable());
	    list.add(future);
	}
	exec.shutdown();
	System.out.println("-------------------------");
	for (int i = 0; i < list.size(); i++) {
	    System.out.println("for inner." + list.size());
	    System.out.println(list.get(i).get());
	}
    }
    
    
    static class TestCallable implements Callable<String> {

	@Override
        public String call() throws Exception {
	    TimeUnit.SECONDS.sleep(2);//休眠2秒
	    System.out.println(Thread.currentThread().getName() + " doning task.....");
	    return Thread.currentThread().getName();
        }
	
    }
}
 