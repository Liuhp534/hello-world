/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.date; 

import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @author	hz16092620 
 * @date	2018年7月27日 下午2:32:24
 * @version      
 */
public class NanoAndMilliTime {
    
    public static void main(String[] args) {
	testTime();
    }
    
    /**
     * （一毫米=1000000纳秒）
     * 毫秒级并不准确
     * 1.currentTimeMillis精度上相对于nanoTime要输很多 
	2.nanoTIme方法本身执行的时间相对于currentTimeMillis要多很多，所以应当只在精确度要求较高时使用nanoTime 
	3.currentTimeMillis是时钟，nanoTime是计时器
     * */
    static void testTime() {
        long nanoWaitForLock = TimeUnit.MILLISECONDS.toNanos(1000);
        System.out.println("nanoWaitForLock : " + nanoWaitForLock);
        long start = System.nanoTime();
        System.out.println("start : " + start);
        try {
            int i = 0;
            while ((System.nanoTime() - start) < nanoWaitForLock) {
        	System.out.println(System.nanoTime());
        	System.out.println(System.nanoTime() - start);
                TimeUnit.MILLISECONDS.sleep(10);  //加随机时间防止活锁
            }
        } catch (Exception e) {
        }
    }

}
 