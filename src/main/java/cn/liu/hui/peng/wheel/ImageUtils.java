package cn.liu.hui.peng.wheel; 

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 
 * 图片处理工具
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月21日 下午3:01:41
 * @version      
 */
public class ImageUtils {

    
    public static void main(String[] args) {
	String fullDescription = "aaa啊啊啊啊<div class=\"a_banner_img\">"
		+ "<img height=\"155\" src=\"http://images.hzins.com/short/www/2012new/images/Public/uploads/health_img/620_jkx5.jpg\" width=\"370\" /></div>";
	System.out.println(getImegeStr(fullDescription));
    }
    
    /**
     * 根据正则获取图片的数据
     * */
    public static String getImegeStr(String str) {
	String result = "";
	if (str == null) {
	    return result;
	}
	Pattern pattern = Pattern.compile("<img.*src=(.*?)[^>]*?>");
	Matcher matcher = pattern.matcher(str);
	while (matcher.find()) {
	    result = matcher.group();
	}
	return result;
    }
}
 