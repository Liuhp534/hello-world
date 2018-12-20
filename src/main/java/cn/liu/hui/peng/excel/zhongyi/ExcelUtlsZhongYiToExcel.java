package cn.liu.hui.peng.excel.zhongyi; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.CollectionUtils;

import cn.liu.hui.peng.excel.CallRecordDTO;
import cn.liu.hui.peng.excel.PolicyRangeDTO;
import cn.liu.hui.peng.excel.SeatDTO;

/**
 * @author	hz16092620 
 * @date	2018年7月6日 上午10:15:29
 * @version      
 */
public class ExcelUtlsZhongYiToExcel {
    
    public static Map<String, Integer> map = new HashMap<>();
    
    public static Map<String, String> repeatMap = new HashMap<>();
    
    static List<ZhongYiDTO> zhongyis = new ArrayList<ZhongYiDTO>(12000);
    
    static Map<Integer, String> platformMap = new HashMap<Integer, String>();
    
    static {
	platformMap.put(1, "主站");
	platformMap.put(2, "齐欣");
	platformMap.put(3, "聚米");
    }

    public static void main(String[] args) throws IOException {
	//获取中意重复保单数据
	getRepeatDB();
	//拼接sql
	analyzisDate();
    }
    
    static void getRepeatDB() throws FileNotFoundException {
	ZhongYiDTO dto = null;
	File file = new File("E:/201810_work/中意数据分析/中意repeat去公式.xls");
	// 获取excel文档
	POIFSFileSystem fs;
	HSSFWorkbook wb;
	HSSFSheet sheetMain;
	try {
	    fs = new POIFSFileSystem(new FileInputStream(file));
	    wb = new HSSFWorkbook(fs);
	    // 读取第一个Sheet
	    sheetMain = wb.getSheetAt(0);
	    HSSFRow row = null;
	    //int totalRow = sheetMain.getLastRowNum() + 1;
	    int anlyzeExcelStart = 1;//excel开始的下标数
	    DecimalFormat df = new DecimalFormat("0");//转换数据类
	    String tempStr = null;
	    Cell temp = null;
	    for (int i = anlyzeExcelStart; i < 9086; i++) {//9086
		dto = new ZhongYiDTO();
		row = sheetMain.getRow(i);
		temp = row.getCell(0);//transNo
		tempStr = df.format(temp.getNumericCellValue());
		dto.setTransNo(tempStr.trim());
		
		temp = row.getCell(1);//insurantId
		tempStr = df.format(temp.getNumericCellValue());
		dto.setInsurantId(tempStr.trim());
		
		temp = row.getCell(3);//platform
		tempStr = temp == null ? "" : temp.toString();
		dto.setPlatform(Integer.valueOf(string2Integer(tempStr, 1)));
		
		
		temp = row.getCell(8);// key
		tempStr = temp == null ? "" : temp.toString();
		
		dto.setKey(tempStr);
		//dto.setTransNo(tempStr);
		//String tempStr = df.format(temp.getNumericCellValue());
		//temp = row.getCell(9);
		zhongyis.add(dto);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	System.out.println(zhongyis.size());
    }
    
    static void analyzisDate() throws IOException {
	String relationSql = "delete from t_insurant_relation where insure_num={0} and insurant_id={1} and policy_company_num in ({2});";
	String policyRouteSql = "delete from t_policy_route where trans_no=''{0}'' and policy_num in ({1});";
	String analysisKey = "";
	StringBuilder relationSb = new StringBuilder();
	StringBuilder policySb = new StringBuilder();
	Map<String, List<String>> dbNumMap = null;
	//--------------------------excel 处理-----------------------------
	File file = new File("E:/201810_work/中意数据分析/中意repeat去公式.xls");
	FileInputStream in = new FileInputStream(file);
	FileOutputStream  out = new FileOutputStream("E:/201810_work/中意数据分析/中意repeat去公式输出.xls");//
	// 获取excel文档
	POIFSFileSystem fs;
	HSSFWorkbook wb;
	HSSFSheet sheetMain;
	try {
	    fs = new POIFSFileSystem(in);
	    wb = new HSSFWorkbook(fs);
	    in.close();
	    // 读取第一个Sheet
	    sheetMain = wb.getSheetAt(0);
	    HSSFRow row = null;
	    HSSFCell cell9 = null;
	    HSSFCell cell10 = null;
	    for (int i = 0, n = zhongyis.size(); i < n; i++) {
		row = sheetMain.getRow(i+1);
		dbNumMap = new HashMap<String, List<String>>();
		analysisKey = getAnalysisKeys(zhongyis.get(i).getKey(), dbNumMap);

		// relation库
		relationSb.append(MessageFormat.format(relationSql, zhongyis.get(i).getTransNo(), zhongyis.get(i).getInsurantId(), analysisKey)).append("\r\n");
		//relationOut.write(relationSb.toString().getBytes());
		cell9 = row.createCell(9);
		cell9.setCellValue(relationSb.toString());
		relationSb = new StringBuilder();

		// policy库
		policySb.append(MessageFormat.format(policyRouteSql, zhongyis.get(i).getTransNo(), analysisKey)).append("\r\n");
		analysisSubmeterTable(dbNumMap, policySb, zhongyis.get(i));
		//policyOut.write(policySb.toString().getBytes());
		cell10 = row.createCell(10);
		cell10.setCellValue(policySb.toString());
		policySb = new StringBuilder();
	    }
	    wb.write(out);
	    out.flush();
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	System.out.println("--------------------执行完成--------------------");
    }
    
    
    
    
    static void analysisSubmeterTable(Map<String, List<String>> dbNumMap, StringBuilder policySb, ZhongYiDTO dto) {
	String policySql = "delete from t_policy_{0} where trans_no=''{1}'' and policy_num in ({2});";// 需要保证在同一个transNo下
	String policyApplicantSql = "delete from t_policy_applicant_{0} where policy_num in ({1});";
	String policyInsurantSql = "delete from t_policy_insurant_{0} where policy_num in ({1});";
	String key = null;
	List<String> value = null;
	for (Object obj : dbNumMap.entrySet()) {
	    @SuppressWarnings("unchecked")
	    Map.Entry<String, List<String>> entry = (Entry<String, List<String>>) obj;
	    key = entry.getKey();
	    value = entry.getValue();
	    if (value != null && value.size() > 0) {
		policySb.append(MessageFormat.format(policySql, key, dto.getTransNo(), changeWhereKey(value))).append("\r\n");
		policySb.append(MessageFormat.format(policyApplicantSql, key, changeWhereKey(value))).append("\r\n");
		policySb.append(MessageFormat.format(policyInsurantSql,key, changeWhereKey(value))).append("\r\n");
	    }
	}
    }
    
    /**将字符串条件加''*/
    static String changeWhereKey(List<String> value) {
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < value.size(); i++) {
	    if ( i != (value.size() - 1)) {
		sb.append("'").append(value.get(i)).append("'").append(",");
	    } else {
		sb.append("'").append(value.get(i)).append("'");
	    }
	}
	return sb.toString();
    }
    
    /**解析policy num条件*/
    static String getAnalysisKeys(String key, Map<String, List<String>> dbNumMap) {
	String[] keys = key.split(",");
	StringBuilder sb = new StringBuilder();
	String policyNum = null;
	for (int i = 0; i < keys.length; i++) {
	    if (keys[i].length() > 0) {
		policyNum = keys[i];
		if (i != (keys.length - 1)) {
		    sb.append("'").append(policyNum).append("'").append(",");
		    if (dbNumMap.get(getDBNum(policyNum)) == null) {
			List<String> list = new ArrayList<String>();
			list.add(policyNum);
			dbNumMap.put(getDBNum(policyNum), list);
		    } else {
			dbNumMap.get(getDBNum(policyNum)).add(policyNum);
		    }
		} else {
		    sb.append("'").append(policyNum).append("'");
		    if (dbNumMap.get(getDBNum(policyNum)) == null) {
			List<String> list = new ArrayList<String>();
			list.add(policyNum);
			dbNumMap.put(getDBNum(policyNum), list);
		    } else {
			dbNumMap.get(getDBNum(policyNum)).add(policyNum);
		    }
		}
	    }
	}
	return sb.toString();
    }
    
    /**获取db的分表号*/
    static String getDBNum(String policyNum) {
	int dbNum = - (policyNum.hashCode() % 10);
	if (dbNum < 0) {
	    dbNum = - dbNum;
	}
	return String.valueOf(dbNum);
    }
    
    /**
     * 1-字符串转Integer，2-转换成分, 3-字符串形式
     * @param str
     * @param type
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年8月15日 上午10:19:26
     * @version      
     */ 
    private static Integer string2Integer(String str, int type) {
	if(str == null || str.length() == 0) {
	    return 0;
	}
	BigDecimal bg = new BigDecimal(str);  
	if(type==2) {
	    bg = bg.multiply(new BigDecimal(100));
	}
	Double doubleValue = bg.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
	Integer i = doubleValue.intValue();
	return i;
    }
    
    /**
     * 1-字符串转Integer，2-转换成分, 3-字符串形式
     * @param str
     * @param type
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年8月15日 上午10:19:26
     * @version      
     */ 
    private static long string2Long(String str, int type) {
	if(str == null || str.length() == 0) {
	    return 0;
	}
	BigDecimal bg = new BigDecimal(str);  
	if(type==2) {
	    bg = bg.multiply(new BigDecimal(100));
	}
	Double doubleValue = bg.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
	long i = doubleValue.longValue();
	return i;
    }
	    
}
 