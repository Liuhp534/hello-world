package cn.liu.hui.peng.thread.future; 

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import cn.liu.hui.peng.date.MathProgramTime;

/**
 * @author	hz16092620 
 * @date	2018年7月17日 下午4:50:24
 * @version      
 */
public class MultithreadFinder {
    
    private static Random random = new Random();
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
	int type = 1;//1单线程，2多线程
	MathProgramTime.start("计算查找最大程序所用时间");
	ExecutorService service = Executors.newFixedThreadPool(2);
	int[] data = new int[10000000];
	for (int i = 0; i < data.length; i++) {
	    data[i] = randomIntTwo();
	    //System.out.println(data[i]);
        }
	
	if (type == 1) {
	    Future<Integer> future1 = service.submit(new FindMax(data, 0, data.length));
	    System.out.println(future1.get());
	} else {
	    Future<Integer> future1 = service.submit(new FindMax(data, 0, data.length / 2));
	    Future<Integer> future2 = service.submit(new FindMax(data, data.length / 2, data.length));
	    System.out.println("the max is : " + Math.max(future1.get(), future2.get()));
	}
	MathProgramTime.end();
    }

    
    public static int randomInt() {
	double d = Math.random() * 100;
	return (int) Math.round(d);
    }
    
    public static int randomIntTwo() {
	return random.nextInt();
    }
}
 