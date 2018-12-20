package cn.liu.hui.peng.wheel; 

import java.text.MessageFormat;

/**
 * <p>
 * 
 * 字符串相关的工具
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月21日 下午2:05:57
 * @version      
 */
public class LiuStringUtils {
    
    /**test入口*/
    public static void main(String[] args) {
	String s1 = "" , s2= "1";
	System.out.println("编辑次数 " + getEditDistance(s1, s2));
	System.out.println(formatStr());
    }
    
    /**获取db的分表号*/
    public static String getDBNum(String policyNum) {
	int dbNum = policyNum.hashCode() % 10;
	if (dbNum < 0) {
	    dbNum = - dbNum;
	}
	return String.valueOf(dbNum);
    }
    
    /**
     * java.text.MessageFormat 拼接string
     * 如果存在引号包含的占位值，单引号用''{x}''，双引号用''''{x}''''
     * */
    public static String formatStr() {
	String policySql = "delete from t_table_{0} where trans_no=''{1}'' and name in ({2});";
	String result = MessageFormat.format(policySql, "10", "20181210", "dage");
	return result;
    }
     
    /**
     * 获得编辑次数，-1表示异常
     * */
    public static int getEditDistance(String s, String t) {  
	if (s == null || t == null) {
	    return -1;
	}
        int d[][]; // matrix  
        int n; // length of s  
        int m; // length of t  
        int i; // iterates through s  
        int j; // iterates through t  
        char s_i; // ith character of s  
        char t_j; // jth character of t  
        int cost; // cost  
          
        // Step 1  
        n = s.length();  
        m = t.length();  
        if (n == 0) {  
            return m;  
        }  
        if (m == 0) {  
            return n;  
        }  
        d = new int[n + 1][m + 1];  
          
        // Step 2  
        for (i = 0; i <= n; i++) {  
            d[i][0] = i;  
        }  
        for (j = 0; j <= m; j++) {  
            d[0][j] = j;  
        }  
          
        // Step 3  
        for (i = 1; i <= n; i++) {  
            s_i = s.charAt(i - 1);  
            // Step 4  
            for (j = 1; j <= m; j++) {  
                t_j = t.charAt(j - 1);  
                // Step 5  
                cost = (s_i == t_j) ? 0 : 1;  
                // Step 6  
                d[i][j] = minNum(d[i - 1][j] + 1, d[i][j - 1] + 1,  
                        d[i - 1][j - 1] + cost);  
            }  
        }  
        // Step 7  
        //print(d, m, n);  
        return d[n][m];  
    }  
    /**
     * 获取最小值
     * */
    private static int minNum(int a, int b, int c) {
	int im = a < b ? a : b;
	return im < c ? im : c;
    }
    /**
     * 打印
     * */
    private static void print(int d[][],int m, int n){  
        for (int i = 0; i <= n; i++) {  
            for (int j = 0; j <= m; j++) {  
                System.out.print(d[i][j]+" ");  
            }  
            System.out.println();  
        }  
    }  
}
 