package cn.liu.hui.peng.string; 

/**
 * @author	hz16092620 
 * @date	2018年5月31日 上午10:43:10
 * @version      
 */
public class TestSwicth {
    public static void main(String[] args) {
	String key = "1";
	
	switch (key) {
	    case "1":
		System.out.println("1");
		break;
	    case "2":
		System.out.println("2");
		break;
	    case "3":
		System.out.println("3");
		break;
	    case "4":
		System.out.println("4");
		break;
	    default:
		System.out.println("...");
		break;
	}
    }

}
 