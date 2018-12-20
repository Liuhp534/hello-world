package cn.liu.hui.peng.locale; 

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 本地化工具NumberFormat、DateFormat、MessageFormat
 * @author	hz16092620 
 * @date	2018年4月19日 下午3:21:43
 * @version      
 */
public class TestLocale {
    
    public static void main(String[] args) {
	//testLocale();
	testDateFormat();
    }
    
    /**
     * NumberFormat转换金额
     * */
    public static void testLocale() {
	Locale locale = Locale.US;
	NumberFormat number = NumberFormat.getCurrencyInstance(locale);
	double amt = 123456.78;
	System.out.println(number.format(amt));
    }

    /**
     * DateFormat转换金额
     * */
    static void testDateFormat() {
	//Locale locale = new Locale("en", "US");    
	Locale locale = new Locale("zh", "CN");    
	Date date = new Date();    
	DateFormat df = DateFormat.getDateInstance(DateFormat.ERA_FIELD, locale);    
	System.out.println(df.format(date));
    }

}
 