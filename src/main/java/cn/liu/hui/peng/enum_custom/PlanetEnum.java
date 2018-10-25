/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.enum_custom; 


/**
 * <p>
 * 
 * 
 * 行星枚举：使用的规则，常用的方法配制。
 * 枚举不可变的、
 * </p>
 * @author	hz16092620 
 * @date	2018年9月5日 下午8:09:34
 * @version      
 */
public enum PlanetEnum {
    
    /**枚举在变量之前*/
    EARTH((byte) 1, "earth"),
    MARS((byte) 2, "mars"),
    VENUS((byte) 3, "venus");
    
    /**枚举不可变的，变量设置成final，添加对应的访问方法*/
    private final byte value;
    
    private final String desc;
    
    
    /**不需要权限修饰符修饰，构造函数*/
    PlanetEnum(byte value, String desc) {
	this.value = value;
	this.desc = desc;
    }

    /**只有访问方法*/
    public byte getValue() {
        return value;
    }
    
    /**只有访问方法*/
    public String getDesc() {
        return desc;
    }
    
    /**
     * 根据value获取对应的枚举
     * */
    public PlanetEnum getByValue(byte value) {
	PlanetEnum result = null;
	if (value <= 0) {
	    return result;
	}
	for (PlanetEnum planetEnum : PlanetEnum.values()) {
	    if (value == planetEnum.value) {
		result = planetEnum;
		break;
	    }
	}
	return result;
    }
    
    public static void main(String[] args) {
	/*for (PlanetEnum planetEnum : PlanetEnum.values()) {
	    System.out.printf("Planet value is %s , desc is %s %n", planetEnum, planetEnum);
	}*/
	System.out.println(PlanetEnum.valueOf("EARTH").getDesc());
	System.out.println(Enum.valueOf(PlanetEnum.class, "EARTH").getDesc());
    }
    

}
 