/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.thread; 

/**
 * @author	hz16092620 
 * @date	2018年7月9日 下午2:58:10
 * @version      
 */
public class ThreadSafeSample {
    
    private int shareState;
    
    public void noSafeFun() {
	while (shareState < 10000) {
	    int format = shareState ++;
	    int latter = shareState;
	    if (format != latter - 1) {
		System.out.println("format = " + format + " latter = " + latter);
	    }
	}
	System.out.println(Thread.currentThread().getName());
    }
    
    public static void main(String[] args) {
	final ThreadSafeSample obj = new ThreadSafeSample();
	Thread a = new Thread(new Runnable() {
	    @Override
	    public void run() {
		obj.noSafeFun();
	    }
	});
	Thread b = new Thread(new Runnable() {
	    @Override
	    public void run() {
		obj.noSafeFun();
	    }
	});
	a.start();
	b.start();
    }

}
 