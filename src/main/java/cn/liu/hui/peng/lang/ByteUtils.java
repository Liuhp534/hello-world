package cn.liu.hui.peng.lang; 

/**
 * byte 工具类
 * @author	hz16092620 
 * @date	2018年8月24日 上午11:50:18
 * @version      
 */
public class ByteUtils {
    
    /**
     * byte 范围-128-127，有符号的byte转int会有负数出现，需要跳转过来。
     * */
    public static int byteToInt(Byte b) {
	int i = b;
	if (i < 0) {
	    return i + 256;
	} else {
	    return i;
	}
    }

}
 