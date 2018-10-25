/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package cn.liu.hui.peng.wheel; 

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <p>
 * 
 * 拼音工具
 * </p>
 * @author	hz16092620 
 * @date	2018年9月21日 下午2:28:57
 * @version      
 */
public class PingYingUtils {

public static void main(String[] args) {
	
	System.out.println(getFirstSpell("刘辉鹏"));
	System.out.println(getFirstSpell("liuhuipeng"));
	System.out.println(getFirstSpell("john"));
	System.out.println(getFirstSpell("John"));
    }
    
    
    /**
     * 获取汉字串拼音首字母，英文字符不变
     * 
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
	StringBuffer pybf = new StringBuffer();
	char[] arr = chinese.toCharArray();
	HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	for (int i = 0; i < arr.length; i++) {
	    if (arr[i] > 128) {
		try {
		    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
		    if (temp != null) {
			pybf.append(temp[0].charAt(0));
			break;
		    }
		} catch (BadHanyuPinyinOutputFormatCombination e) {
		    e.printStackTrace();
		}
	    } else {
		pybf.append(arr[i]);
		break;
	    }
	}
	return pybf.toString().replaceAll("\\W", "").trim().toLowerCase();
    }
}
 