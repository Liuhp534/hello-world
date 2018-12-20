package cn.liu.hui.peng.excel; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.CollectionUtils;

/**
 * @author	hz16092620 
 * @date	2018年7月6日 上午10:15:29
 * @version      
 */
public class ExcelUtls2 {
    
    public static Map<String, Integer> map = new HashMap<>();
    
    public static Map<String, String> repeatMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
	//获取前海重复保单数据
	getRepeatDB();
	//获取数据库中的数据
	//getDB();
	//设置excel表中的数据
	setObjs();
	/*List<String> list = new ArrayList<String>();
	list.add("20180926012203");
	list.add("20180926012203");
	System.out.println(list.size());*/
    }
    
    static void getRepeatDB() {
	File file = new File("E:/201810_work/pluto_repeat_ids.xls");
	// 获取excel文档
	POIFSFileSystem fs;
	HSSFWorkbook wb;
	HSSFSheet sheetMain;
	int count = 0;
	try {
	    fs = new POIFSFileSystem(new FileInputStream(file));
	    wb = new HSSFWorkbook(fs);
	    // 读取第一个Sheet
	    sheetMain = wb.getSheetAt(0);
	    int totalRow = sheetMain.getLastRowNum() + 1;
	    int anlyzeExcelStart = 0;
	    int totalCell = 5;
	    CallRecordDTO callDto = null;
	    SeatDTO seatDto = null;
	    DecimalFormat df = new DecimalFormat("0");
	    Cell temp = null;
	    Cell temp1 = null;
	    for (int i = anlyzeExcelStart; i < 378; i++) {//13708
		HSSFRow row = sheetMain.getRow(i);
		temp = row.getCell(0);
		String tempStr = temp == null ? "" : temp.toString();
		//String tempStr = df.format(temp.getNumericCellValue());
		repeatMap.put(tempStr, tempStr);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	System.out.println(repeatMap.get("20180926033053"));
	System.out.println(repeatMap.size());
    }
    
   static void getDB()  throws IOException {
	File file = new File("E:/201810_work/idtemp2.xls");
	// 获取excel文档
	POIFSFileSystem fs;
	HSSFWorkbook wb;
	HSSFSheet sheetMain;
	int count = 0;
	try {
	    fs = new POIFSFileSystem(new FileInputStream(file));
	    wb = new HSSFWorkbook(fs);
	    // 读取第一个Sheet
	    sheetMain = wb.getSheetAt(0);
	    int totalRow = sheetMain.getLastRowNum() + 1;
	    int anlyzeExcelStart = 0;
	    int totalCell = 5;
	    CallRecordDTO callDto = null;
	    SeatDTO seatDto = null;
	    DecimalFormat df = new DecimalFormat("0");
	    Cell temp = null;
	    Cell temp1 = null;
	    for (int i = anlyzeExcelStart; i < 9966; i++) {//13708
		HSSFRow row = sheetMain.getRow(i);
		temp = row.getCell(0);
		//String tempStr = temp == null ? "" : temp.toString();
		String tempStr = df.format(temp.getNumericCellValue());
		
		temp1 = row.getCell(1);//数量
		//String tempStr1 = temp1 == null ? "" : temp1.toString();
		String tempStr1 = df.format(temp1.getNumericCellValue());
		Integer num = string2Integer(tempStr1, 1);
		map.put(tempStr, num);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	System.out.println(map.get("20180930038490"));
	System.out.println(map.size());
    }
    
    public static void setObjs() throws IOException {
	File file = new File("E:/201810_work/to-pluto_is_web_count.xls");
	// 获取excel文档
	POIFSFileSystem fs;
	HSSFWorkbook wb;
	HSSFSheet sheetMain;
	int count = 0;
	//List<String> partnerIds =  new ArrayList<String>();
	Set<String> partnerIds = new LinkedHashSet<>();
	try {
	    fs = new POIFSFileSystem(new FileInputStream(file));
	    wb = new HSSFWorkbook(fs);
	    // 读取第一个Sheet
	    sheetMain = wb.getSheetAt(0);
	    int totalRow = sheetMain.getLastRowNum() + 1;
	    int anlyzeExcelStart = 3;// 5003 15003 28713
	    int totalCell = 5;
	    CallRecordDTO callDto = null;
	    SeatDTO seatDto = null;
	    DecimalFormat df = new DecimalFormat("0");
	    Cell temp = null;
	    Cell temp1 = null;
	    List<PolicyRangeDTO> policyRangeDTOs = new ArrayList<PolicyRangeDTO>();
	    PolicyRangeDTO policyRangeDTO = null;
	    StringBuilder sb = new StringBuilder();
	    FileOutputStream out = new FileOutputStream("E:/201810_work/insureNums.sql");
	    int repeatNum = 0;//统计前海保单号重复的数量
	    for (int i = anlyzeExcelStart; i < 28713; i++) {
		HSSFRow row = sheetMain.getRow(i);
		temp = row.getCell(1);//被保人数量
		String tempStr = temp == null ? "" : temp.toString();
		//String tempStr = df.format(temp.getNumericCellValue());
		Integer num = string2Integer(tempStr, 1);
		
		temp1 = row.getCell(9);//relation数量
		//String tempStr1 = temp1 == null ? "" : temp1.toString();
		String tempStr1 = df.format(temp1.getNumericCellValue());
		Integer num1 = string2Integer(tempStr1, 1);
		if (num.intValue() != num1.intValue()) {
		    policyRangeDTO = new PolicyRangeDTO();
		    policyRangeDTO.setInsureNumStr(row.getCell(0).toString());
		    policyRangeDTO.setInsurantCount(num);
		    policyRangeDTO.setRelationCount(num1);
		    if (repeatMap.get(policyRangeDTO.getInsureNumStr()) != null) {
			policyRangeDTO.setType(1);
			repeatNum ++;
			repeatMap.remove(policyRangeDTO.getInsureNumStr());
		    } else {
			policyRangeDTO.setType(2);
		    }
		    policyRangeDTOs.add(policyRangeDTO);
		}
		//partnerIds.add(tempStr.trim());
	    }
	    System.out.println(repeatMap.toString());
	    Collections.sort(policyRangeDTOs);
	    String tempStr = "  ,";
	    for (int i = 0; i < policyRangeDTOs.size(); i++) {
		if (String.valueOf(policyRangeDTOs.get(i).getInsurantCount()).length() == 1) {
		    tempStr = "   ,";
		} else if (String.valueOf(policyRangeDTOs.get(i).getInsurantCount()).length() == 2) {
		    tempStr = "  ,";
		} else {
		    tempStr = " ,";
		}
		sb.append(policyRangeDTOs.get(i).getInsureNumStr()).append(",").append(policyRangeDTOs.get(i).getType()).append(",")
		.append(policyRangeDTOs.get(i).getInsurantCount()).append(tempStr).append(policyRangeDTOs.get(i).getRelationCount()).append("\r\n");
		out.write(sb.toString().getBytes());
		sb = new StringBuilder();
	    }
	    System.out.println("repeatNum : " + repeatNum);
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	/*System.out.println(partnerIds.size());
	StringBuilder sb = new StringBuilder();
	FileOutputStream out = new FileOutputStream("E:/201810_work/insureNums2.sql");
	int i = 0;
	for (String str : partnerIds) {
	    if (map.get(str) != null) {
		sb.append(str).append(",").append(map.get(str).toString()).append("\r\n");
	    } else {
		sb.append(str).append(",").append("0").append("\r\n");
	    }
	    out.write(sb.toString().getBytes());
	    sb = new StringBuilder();
	    i ++;
	}
	System.out.println(i);
	out.close();*/
	/*int i = 0;
	for (String str : partnerIds) {
	    sb.append(str).append(",");
	    out.write(sb.toString().getBytes());
	    sb = new StringBuilder();
	    i ++;
	}
	System.out.println(i);*/
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
 