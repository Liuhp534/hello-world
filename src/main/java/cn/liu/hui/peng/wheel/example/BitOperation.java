package cn.liu.hui.peng.wheel.example; 

/**
 * <p>
 * 
 * 位运算
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月21日 下午2:33:42
 * @version      
 */
public class BitOperation {
    
    
    public static void main(String[] args) {
	//取余 0 1 2 0 2
  	System.out.println(0%3);
  	System.out.println(1%3);
  	System.out.println(2%3);
  	System.out.println(3%3);
  	System.out.println(20%3);
  	
	//与 操作，两者相同则相同
  	System.out.println(6 & 1);//110 & 001 = 000 结果 0
  	System.out.println(6 & 2);//110 & 010 = 010 结果 2
  	System.out.println(6 & 4);//110 & 100 = 100 结果 4
  	
  	//移动运算符 
  	System.out.println(6 << 1);//12
  	System.out.println(6 >> 1);//3
      }
}
 