package cn.liu.hui.peng.enum_custom; 

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 运算操作符
 * </p>
 * @author	hz16092620 
 * @date	2018年9月6日 上午11:25:24
 * @version      
 */
public enum OperationEnum {

    PLUS("+") {

	@Override
	double apply(double x, double y) {
	    print(x , y);
	    return x + y;
	}
	
	public void print(double x, double y) {
	    System.out.println("param is x = " + x + " y = " + y);
	}
    },
    MINUS("-") {

	@Override
	double apply(double x, double y) {
	    return x - y;
	}
    },
    TIMES("*") {

	@Override
	double apply(double x, double y) {
	    return x * y;
	}
    },
    DIVIDE("/") {

	@Override
	double apply(double x, double y) {
	    return x / y;
	}
    };
    
    /**变量*/
    private final String symbol;
    
    /**构造函数*/
    OperationEnum(String symbol) {
	this.symbol = symbol;
    }
    
    
    @Override
    public String toString() {
	return this.symbol;
    }

    /**特定于常量的方法实现*/
    abstract double apply(double x, double y);
    
    /**通过value获取enum的静态变量*/
    private static final Map<String, OperationEnum> valueToEnum = new HashMap<>();
    
    static {
	for (OperationEnum operationEnum : OperationEnum.values()) {
	    valueToEnum.put(operationEnum.getSymbol(), operationEnum);
	}
    }
    
    /**新增一个空间去保存，占内存*/
    public static OperationEnum getByValue(String value) {
	return valueToEnum.get(value);
    }
    
    
    public String getSymbol() {
        return symbol;
    }


    /**
     * 比较危险，如果新增枚举那么可能由于没有更新导致问题。
     * */
    /*double apply (double x, double y) {
	switch (this) {
	    case PLUS : return x + y;
	    case MINUS : return x - y;
	    case TIMES : return x * y;
	    case DIVIDE : return x / y;
	}
	throw new RuntimeException();
    }*/
    
    public static void main(String[] args) {
	double x = 2.000;
	double y = 4.000;
	//System.out.println(String.format("%f %s %f = %f %n", x, OperationEnum.PLUS, y, OperationEnum.PLUS.apply(x, y)));
	/*System.out.println(8 << 1);
	System.out.println(8 >> 1);*/
	OperationEnum.DIVIDE.PLUS.apply(x, y);
    }
}
 