/**
 * Copyright (c) 2006-2016 Huize Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Huize,http://www.huize.com.
 *  
 */   
package cn.liu.hui.peng.excel; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.RightMarginRecord;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;


/**
 * @author	hz16092620 
 * @date	2018年3月9日 下午5:34:44
 * @version      
 */
public class ExcelUtils {
    
    public static Map<Integer, SeatDTO> seatMap = new HashMap<Integer, SeatDTO>();
    
    public static List<CallRecordDTO> callDtos = new ArrayList<CallRecordDTO>(250);
    
    static {
	seatMap.put(14, new SeatDTO(14, "孙凌", 1,  "hz16042256", ""));
	seatMap.put(18, new SeatDTO(18, "田荣", 1,  "hz15041228", "15391988362"));
	seatMap.put(26, new SeatDTO(26, "巢达娟", 1,  "hz16072430", "15391988736"));
	seatMap.put(30, new SeatDTO(30, "陈莉军", 1,  "hz15061427", "15391988265"));
	seatMap.put(31, new SeatDTO(31, "刘二宁", 1,  "hz15041266", "15391988380"));
	seatMap.put(33, new SeatDTO(33, "周登香", 1,  "hz15041260", ""));
	seatMap.put(34, new SeatDTO(34, "何丽丽", 1,  "hz15101722", "18225856725"));
	seatMap.put(37, new SeatDTO(37, "王秀娟", 1,  "hz15081568", "15391988293"));
	seatMap.put(38, new SeatDTO(38, "李娜", 1,  "hz15061422", "15391988290"));
	seatMap.put(39, new SeatDTO(39, "邹小惠", 1,  "hz15101762", ""));
	seatMap.put(81, new SeatDTO(81, "刘翻红", 2,  "hz16102648", ""));
	seatMap.put(82, new SeatDTO(82, "赵婵婵", 1,  "hz17032909", ""));
	seatMap.put(90, new SeatDTO(90, "屈洁", 2,  "hz15081560", ""));
    }

    public static void main(String[] args) throws IOException {
	//第一步获取代理人id
	//select contact_number,partner_id from personal_agent_partner where partner_id in 
	//select partner_id,partner_name,seat_id from seat_partner where partner_id in 
	//analyzeExcel(null, true);
	
	//解析数据
	setObjs();
	
	//获取sql语句
	String dataSqlStr = "";
	String temp = "\r\n";
	//FileOutputStream out = new FileOutputStream("E:/201803_work/沟通记录601-1000.sql");
	//FileOutputStream out = new FileOutputStream("E:/201803_work/沟通记录1001-1400.sql");
	//FileOutputStream out = new FileOutputStream("E:/201803_work/沟通记录1401-1800.sql");
	//FileOutputStream out = new FileOutputStream("E:/201803_work/沟通记录1801-2200.sql");
	//FileOutputStream out = new FileOutputStream("E:/201803_work/沟通记录2201-2502.sql");
	//FileOutputStream out = new FileOutputStream("E:/201803_work/沟通记录0-1721.sql");
	FileOutputStream out = new FileOutputStream("E:/201806_work/沟通记录0-253.sql");
	for (int i = 0; i < callDtos.size(); i++) {
	    dataSqlStr = setData(callDtos.get(i));
	    dataSqlStr += temp;
	    out.write(dataSqlStr.getBytes());
        }
	out.close();
    }
    
    
    public static void setObjs() {
	//File file = new File("E:/201803_work/代理人信息表0-200.xls");
	//File file = new File("E:/201803_work/代理人信息表200-600.xls");
	//File file = new File("E:/201803_work/代理人信息表601-1000.xls");
	//File file = new File("E:/201803_work/代理人信息表1001-1400.xls");
	//File file = new File("E:/201803_work/代理人信息表1401-1800.xls");
	//File file = new File("E:/201803_work/代理人信息表1801-2200.xls");
	//File file = new File("E:/201803_work/代理人信息表2201-2502.xls");
	//File file = new File("E:/201803_work/代理人信息表0-1721.xls");
	File file = new File("E:/201806_work/代理人信息表0-260.xls");
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
	    int anlyzeExcelStart = 1;
	    int totalCell = 5;
	    CallRecordDTO callDto = null;
	    SeatDTO seatDto = null;
	    DecimalFormat df = new DecimalFormat("0");
	    for (int i = anlyzeExcelStart; i < 254; i++) {
		HSSFRow row = sheetMain.getRow(i);
		callDto = new CallRecordDTO();
		for (int j = 0; j < 4; j++) {
		    Cell temp = row.getCell(j);
		    String tempStr = temp == null ? "" : temp.toString();
		    if (j == 0) {
			callDto.setPartnerId(string2Integer(tempStr, 1).toString());
		    } else if (j == 1) {
			callDto.setPartnerName(tempStr);
		    } else if (j == 2) {
			callDto.setSeatId(string2Integer(tempStr, 1).toString());
			if (seatMap.get(string2Integer(tempStr, 1)) != null) {
			    seatDto = seatMap.get(string2Integer(tempStr, 1));
			    callDto.setSeatName(seatDto.getSeatName());
			    callDto.setSeatNum(seatDto.getSeatNum());
			    callDto.setSeatPhone(seatDto.getSeatPhone());
			    callDto.setSeatType(seatDto.getSeatType().toString());
			} else {
			    callDto.setSeatName("");
			    callDto.setSeatNum("");
			    callDto.setSeatPhone("");
			    callDto.setSeatType("");
			}
		    } else if (j == 3) {
			if (tempStr.indexOf("-") != -1) {
			    callDto.setPartnerPhone(tempStr);
			} else {
			    callDto.setPartnerPhone(df.format(row.getCell(j).getNumericCellValue()));
			}
		    } 
		}
		callDtos.add(callDto);
		count++;// 统计解析行数
	    }
	} catch (IOException e) {
	    System.out.println("出错的行数: " + count);
	    e.printStackTrace();
	}
	System.out.println("呼叫个数 ：" + callDtos.size());
	Map<String, CallRecordDTO> callRecordMap = new HashMap<String, CallRecordDTO>();
	for (int i = 0; i < callDtos.size(); i++) {
	    callRecordMap.put(callDtos.get(i).getPartnerId(), callDtos.get(i));
        }
	analyzeExcel(callRecordMap, false);
    }
    
    public static void analyzeExcel(Map<String, CallRecordDTO> callRecordMap, boolean printPartnerId) {
	File file = new File("E:/201806_work/aaaa.xls");
	// 获取excel文档
	POIFSFileSystem fs;
	HSSFWorkbook wb;
	HSSFSheet sheetMain;
	List<Integer> partnerIds = new ArrayList<Integer>(2600);
	int count = 0;
        try {
	    fs = new POIFSFileSystem(new FileInputStream(file));
	    wb = new HSSFWorkbook(fs);
	    // 读取第一个Sheet
	    sheetMain = wb.getSheetAt(0);
	    int totalRow = sheetMain.getLastRowNum() + 1;
	    int anlyzeExcelStart = 1;
	    int totalCell = 5;
	    CallRecordDTO callRecordDto = null;
	    String remarkTemp = "";
	    for (int i = anlyzeExcelStart; i < 260; i++) {
		HSSFRow row = sheetMain.getRow(i);
		for (int j = 0; j < 1; j++) {
		    Cell temp = row.getCell(j);
		    String tempStr = temp == null ? "" : temp.toString();
		    if (!printPartnerId) {
			if (callRecordMap.get(string2Integer(tempStr, 1).toString()) != null) {
			    callRecordDto = callRecordMap.get(string2Integer(tempStr, 1).toString());
			    //1：坐席接听，2：坐席未接听，3：客户接听，4：客户未接听
			    callRecordDto.setCallResult(row.getCell(3) == null ? "" : string2Integer(row.getCell(3).toString(), 1).toString());
			    callRecordDto.setFirstSolve(row.getCell(5) == null ? "" : string2Integer(row.getCell(5).toString(), 1).toString());
			    remarkTemp = row.getCell(1) == null ? "" : row.getCell(1).toString();
			    if (DateUtil.isCellDateFormatted(row.getCell(2))) {
				remarkTemp += " , " + date2String(row.getCell(2).getDateCellValue());
			    } 
			    callRecordDto.setRecordDesc(remarkTemp);
			    //1：联系成功，2：联系失败，3：拒绝联系
			    callRecordDto.setRecordResult(row.getCell(4) == null ? "" : string2Integer(row.getCell(4).toString(), 1).toString());
			}
		    } else {
			partnerIds.add(string2Integer(tempStr, 1));
		    }
		}
		count++;// 统计解析行数
		/*
	         * if (partnerIds.size() == 1000) { break; }
	         */
	    }
        } catch (IOException e) {
            System.out.println("出错的行数: " + count);
	    e.printStackTrace();
        }
        
	if (printPartnerId) {
	    System.out.println(partnerIds.size());
	    Collections.sort(partnerIds);
	    int printCount = 0;
	    StringBuilder sb = new StringBuilder();
	    sb.append("(");
	    for (int i = 0; i < partnerIds.size(); i++) {
		printCount++;
		sb.append(partnerIds.get(i)).append(",");
		/*if (printCount == 200 || i == (partnerIds.size() - 1)) {
		    System.out.println(printCount);
		    printPartnerIds(sb);
		    sb = new StringBuilder();
		    sb.append("(");
		    printCount = 0;
		}*/
	    }
	    System.out.println(printCount);
	    printPartnerIds(sb);
	}
    }
    
    
    private static void printPartnerIds(StringBuilder sb) {
	String result = sb.substring(0, sb.length() - 1) + ")";
        System.out.println(result);
    }
    
	/*CallRecordDTO dto = new CallRecordDTO();
	dto.setCallResult("3");//1：坐席接听，2：坐席未接听，3：客户接听，4：客户未接听
	dto.setFirstSolve("1");
	dto.setPartnerId("536594");
	dto.setPartnerName("英英");
	dto.setPartnerPhone("13123232564");
	dto.setRecordDesc("怎么样了啊啊啊啊啊");
	dto.setRecordResult("1");//1：联系成功，2：联系失败，3：拒绝联系
	dto.setSeatId("22");
	dto.setSeatName("彭瑞鸾");
	dto.setSeatNum("hz16112706");
	dto.setSeatPhone("075549998687");
	dto.setSeatType("2");
	System.out.println(setData(dto));*/
    public static String setData(CallRecordDTO dto) {
	String pre = "INSERT INTO `call_record` (`call_unique_id`, `call_direction`, `call_type`, `call_result`, `dial_time`, `ring_time`, `answer_time`, `hangup_time`, `answer_delay`, `call_duration`, `seat_id`, `seat_num`, `seat_name`, `seat_type`, `seat_phone`, `partner_id`, `partner_name`, `partner_type`, `partner_phone`, `satisfaction`, `sound_file`, `first_solve`, `record_result`, `record_label`, `record_task`, `record_order`, `record_desc`, `sync_time`, `last_modify_time`, `remark`) ";
	String value = "VALUES('''',''2'',''2'',''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''0'',''600'',''{5}'',''{6}'',''{7}'',''{8}'',''{9}'',''{10}'',''{11}'',''1'',''{12}'',''0'','''',''{13}'',''{14}'','''','''','''',''{15}'',''0000-00-00 00:00:00'',''0000-00-00 00:00:00'','''');";
	//System.out.println(value);
	String callResult = dto.getCallResult();
	String dialTime = dto.getDialTime();
	String ringTime = dto.getRingTime();
	String  answerTime = dto.getAnswerTime();
	String  hangupTime = dto.getHangupTime();
	String  seatId = dto.getSeatId();
	String  seatNum = dto.getSeatNum();
	String  seatName = dto.getSeatName();
	String  seatType = dto.getSeatType();
	String  seatPhone = dto.getSeatPhone();
	String  partnerId = dto.getPartnerId();
	String  partnerName = dto.getPartnerName();
	String  partnerPhone = dto.getPartnerPhone();
	String  firstSolve = dto.getFirstSolve();
	String  recordResult = dto.getRecordResult();
	String  recordDesc = dto.getRecordDesc();
	value = MessageFormat.format(value, callResult, dialTime, ringTime, answerTime, hangupTime, seatId, seatNum
		, seatName, seatType, seatPhone, partnerId, partnerName, partnerPhone, firstSolve, recordResult, recordDesc);
	return pre + value;
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
    
    private static String date2String(Date date) {
	DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formater.format(date);
        return dateStr;
    }
}
 