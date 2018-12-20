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
 