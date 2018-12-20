package cn.liu.hui.peng.proxy; 

/**
 * @author	hz16092620 
 * @date	2018年4月23日 下午8:01:52
 * @version      
 */
public class UserServiceImpl implements UserService {

    @Override
    public String sayMorning(String name) {
	System.out.println("say hello....");
	return name;
    }

}
 