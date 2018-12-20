package cn.liu.hui.peng.effective.clone; 

import java.util.Arrays;
import java.util.Collections;

/**
 * <p>
 * 
 * clone 测试克隆方法
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月30日 下午6:18:16
 * @version      
 */
public class PhoneNumber implements Cloneable {
    
    
    public static void main(String[] args) throws CloneNotSupportedException {
	PhoneNumber pn = new PhoneNumber(10, "beijing");
	PhoneNumber clonePn = pn.clone();
	System.out.println(clonePn.getAreaCode());
	//Collections.sort(list);
	//Arrays.sort(a);
    }
    
    private int phone;
    
    private String areaCode;

    public PhoneNumber (int phone, String areaCode) {
	this.phone = phone;
	this.areaCode = areaCode;
    }
    
    public int getPhone() {
        return phone;
    }

    
    public void setPhone(int phone) {
        this.phone = phone;
    }

    
    public String getAreaCode() {
        return areaCode;
    }

    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    @Override
    protected PhoneNumber clone() throws CloneNotSupportedException {
	return (PhoneNumber) super.clone();
    }
    
    

}
 