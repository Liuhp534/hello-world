package cn.liu.hui.peng.decimal; 

import java.text.DecimalFormat;

/**
 * decimalformat 用于格式化数据
 * @author	hz16092620 
 * @date	2018年3月16日 上午10:28:43
 * @version      
 */
public class TestDecimal {
    
    public static void main(String[] args) {
	decimal();
    }
    
    private static void decimal() {
	DecimalFormat format = new DecimalFormat("JM0000");
	Integer i = 114;
	System.out.println(format.format(i));
    }

}
 