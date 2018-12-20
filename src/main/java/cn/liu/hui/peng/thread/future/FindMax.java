package cn.liu.hui.peng.thread.future; 

import java.util.concurrent.Callable;

/**
 * 找最大值
 * @author	hz16092620 
 * @date	2018年7月17日 下午4:44:00
 * @version      
 */
public class FindMax implements Callable<Integer> {
    
    private int start;
    
    private int end;
    
    private int[] data;
    
    public FindMax(int[] data, int start, int end) {
	this.data = data;
	this.start = start;
	this.end = end;
    }
    
    @Override
    public Integer call() throws Exception {
	int max = Integer.MIN_VALUE;
	for (int i = start; i < end; i++) {
	    if (data[i] > max) {
		max = data[i];
	    }
        }
	return max;
    }

}
 