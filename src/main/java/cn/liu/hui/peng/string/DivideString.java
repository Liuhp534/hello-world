package cn.liu.hui.peng.string; 

import java.util.Scanner;

/**
 * @author	hz16092620 
 * @date	2018年3月15日 下午4:50:40
 * @version      
 */
public class DivideString {
    
    public static void main(String[] args) {
	/*Scanner scanner = new Scanner(System.in);
	
	System.out.println("enter your full name: ");
	String fullName = scanner.nextLine();
	
	int fristNameIndex = fullName.indexOf(" ");
	String firstName = fullName.substring(0, fristNameIndex);
	String lastName = fullName.substring(fristNameIndex + 1);
	
	System.out.println("\n" + "firstName " + firstName + " lastName " + lastName);
	
	scanner.close();*/
	String str = "P55010106762018L577417.P55010106762018L577413..P55010106762018L577421";//两个分隔符之间是空不是null
	String[] strs = str.split(",");
	for (String string : strs) {
	    System.out.println(string + " length = " + string.length());
	}
    }

}
 