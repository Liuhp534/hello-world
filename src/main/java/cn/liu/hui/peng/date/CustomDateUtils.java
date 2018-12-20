package cn.liu.hui.peng.date; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.liu.hui.peng.string.CustomStringUtils;


/**
 * @author	hz16092620 
 * @date	2018年4月11日 上午9:27:11
 * @version      
 */
public class CustomDateUtils {
    
    /**日期*/
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    
    /**24小时制，日期+时间*/
    public static final String YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    
    public static void main(String[] args) {
	long l = 1524107289622L;
	System.out.println(date2String(getDateByLong(l), YYYY_MM_DD_HH_mm_ss));
    }
    

    /**
     * @return
     *  根据long获取时间
     * @author	hz16092620 
     * @date	2018年4月11日 上午9:35:03
     * @version      
     */ 
    public static Date getDateByLong(long l) {
	Date date = new Date(1523409945134L);
	return date;
    }
    
    /**
     * 字符串日期转日期(DateTime) YYYY_MM_DD
     * @param dateStr
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年9月13日 上午10:32:11
     * @version      
     */ 
    public static Date string2Date(String dateStr) {
	try {
	    Date date = new SimpleDateFormat(YYYY_MM_DD).parse(dateStr);
	    return new Date(date.getTime());
	} catch (Exception e) {
	    System.out.println("日期转换失败！数据为:" + dateStr + "，格式为：" + YYYY_MM_DD);
	    return null;
	}
    }

    /**
     * 字符串日期转日期 默认格式：YYYY_MM_DD
     * @param dateStr
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年9月13日 上午10:32:11
     * @version      
     */ 
    public static Date string2Date(String dateStr, String format) {
	if(CustomStringUtils.isEmpty(format)) {
	    format = YYYY_MM_DD;
	}
	SimpleDateFormat sd = new SimpleDateFormat(format);
	try {
	    return sd.parse(dateStr);
	} catch (ParseException e) {
	    System.out.println(dateStr + ", 日期转:" +format +"格式失败！！！");
	}
	return null;
    }

    /**
     * 日期转字符串 默认格式：YYYY_MM_DD
     * @param dateStr
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年9月13日 上午10:32:11
     * @version      
     */ 
    public static String date2String(Date date, String format) {
	if (CustomStringUtils.isEmpty(format)) {
	    format = "yyyy-MM-dd";
	}
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	try {
	    return sdf.format(date);
	} catch (Exception e) {
	    System.out.println(date + ", 日期转:" +format +"格式失败！！！");
	}
	return null;
    }
    
    public static Date parseDate(String str, String formate){
	SimpleDateFormat dateFormate = new SimpleDateFormat(formate);
	try {
	    return dateFormate.parse(str);
	} catch (ParseException e) {
	   return null;
	}
    }
    
    /**
     * 获取相隔天数，错误则返回null
     * 开始日期为：2015-05-28 11:00:00，结束日期为：2015-05-29 10:59:59，相差天数为0，差1秒，相隔天数为1
     * @param endDate
     * @param startDate
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年9月12日 下午4:26:15
     * @version      
     * @throws ParseException 
     */ 
    public static Integer getBetweenDay(Date startDate, Date endDate){
	SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
        try {
            Date tempStartDate = format.parse(date2String(startDate, YYYY_MM_DD));
            Date tempEndDate = format.parse(date2String(endDate, YYYY_MM_DD));
            Long between = (tempEndDate.getTime() - tempStartDate.getTime()) / (1000*60*60*24);
            return between.intValue();
        } catch (ParseException e) {
            System.out.println("获取相隔天数失败！数据为:startDate=" + startDate + ",endDate=" + endDate);
        }
	return null;
    }
    
    /**
     * 获取时间戳
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年9月13日 上午10:43:42
     * @version      
     */ 
    public static Date getTimeInMillis() {
	return new Date(System.currentTimeMillis());
    }
    
    /**
     * 增加或者减去天数
     * @param date
     * @param days
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年9月13日 下午7:11:01
     * @version      
     */ 
    public static Date changeDay(Date date, int days) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.DATE, days);
	Date result = calendar.getTime();
	return result;
    }
    
    public static boolean isDateStr(String dateStr) {
	// 使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
	Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
	Matcher m = p.matcher(dateStr);
	return m.matches();
    }

}
 