package cn.liu.hui.peng.interface_coustom; 

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

/**
 * <p>
 * 重复利用的排序工具
 * </p>
 * @author	hz16092620 
 * @date	2018年9月3日 上午11:24:26
 * @version      
 */
public class InnerComparator {
    
    
    private static class StringComparator implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
	    return o1.length() - o2.length();
	}
    }
    
    /**
     * 公有的属性
     * */
    public static final Comparator<String> COMPARATOR_UTILS = new StringComparator();
    
    
    public static void main(String[] args) {
	String str1 = "liu";
	String str2 = "peng";
	String str3 = "a";
	String str4 = "bb";
	String str5 = "z";
	List<String> lists = new LinkedList<>();
	lists.add(str1);
	lists.add(str2);
	lists.add(str3);
	lists.add(str4);
	lists.add(str5);
	System.out.println(lists);
	Collections.sort(lists, InnerComparator.COMPARATOR_UTILS);
	System.out.println(lists);
    }

}
 