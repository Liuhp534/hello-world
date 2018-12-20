package cn.liu.hui.peng.lang; 

/**
 * @author	hz16092620 
 * @date	2018年5月3日 下午2:42:59
 * @version      
 */
public class TestLong {
    
    private Long a;
    
    public static void main(String[] args) {
	Integer a = 100;
	TestLong test = new TestLong();
	test.setA(a.longValue());
	System.out.println(test.getA());
	
	//数字有横线
	long l = 123_456;
	System.out.println(l);
    }

    
    public Long getA() {
        return a;
    }

    
    public void setA(Long a) {
        this.a = a;
    }
    
    

}
 