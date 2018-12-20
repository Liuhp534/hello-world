package cn.liu.hui.peng.thread.syn; 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.liu.hui.peng.date.CustomDateUtils;

/**
 * @author	hz16092620 
 * @date	2018年7月17日 下午7:55:08
 * @version      
 */
public class LogFile {
    
    
    public static void main(String[] args) {
	LogFile logFile = new LogFile(new File("E:/201807_work/test.txt"));
	ExecutorService service = Executors.newFixedThreadPool(10);
	TaskTest task1 = logFile.new TaskTest();
	TaskTest task2 = logFile.new TaskTest();
	TaskTest task3 = logFile.new TaskTest();
	TaskTest task4 = logFile.new TaskTest();
        service.submit(task1);
        service.submit(task2);
        service.submit(task3);
        service.submit(task4);
        service.shutdown();
    }

    private Writer out;
    
    public LogFile(File file) {
	try {
	    FileWriter fw = new FileWriter(file, true);
	    this.out = new BufferedWriter(fw);
	} catch (IOException e) {
	    e.printStackTrace();
        }
    }
    
    public void writeSome(String message) {
	Date d = new Date();
	String threadName = Thread.currentThread().getName();
	threadName += " : ";
	try {
	    this.out.write(threadName + CustomDateUtils.date2String(d, CustomDateUtils.YYYY_MM_DD_HH_mm_ss));
	    this.out.write(threadName + "\t");
	    this.out.write(threadName + message);
	    this.out.write(threadName + "\n");
        } catch (IOException e) {
	    e.printStackTrace();
        }
    }
    
    public void close() {
	try {
	    if (this.out != null) {
		this.out.flush();
		this.out.close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**内部任务类*/
    class TaskTest implements Callable<Boolean> {
	@Override
        public Boolean call() throws Exception {
	    LogFile logFile = new LogFile(new File("E:/201807_work/test.txt"));
	    logFile.writeSome("aaa");
	    logFile.close();
	    return true;
        }
    }
    
}
 