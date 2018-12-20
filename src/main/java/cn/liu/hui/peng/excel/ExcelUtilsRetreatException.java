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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
public class ExcelUtilsRetreatException {
    
    public static Map<Integer, SeatDTO> seatMap = new HashMap<Integer, SeatDTO>();
    
    public static List<CallRecordDTO> callDtos = new ArrayList<CallRecordDTO>(250);
    

    public static void main(String[] args) throws IOException {
	//解析数据
	setObjs();
    }
    
    
    public static void setObjs() {
	File file = new File("E:\\201812_work\\修复数据\\工号.xls");
	// 获取excel文档
	POIFSFileSystem fs;
	HSSFWorkbook wb;
	HSSFSheet sheetMain;
	int count = 0;
	List<String> partnerIds = new LinkedList<String>();
	Set<String> retreatNums =  new TreeSet<String>();
	try {
	    fs = new POIFSFileSystem(new FileInputStream(file));
	    wb = new HSSFWorkbook(fs);
	    // 读取第一个Sheet
	    sheetMain = wb.getSheetAt(0);
	    DecimalFormat df = new DecimalFormat("0");
	    for (int i = 9000; i < 9336; i++) {//0 1000 3000 5000 7000 8000 9000 9336
		HSSFRow row = sheetMain.getRow(i);
		Cell temp = row.getCell(0);
		String tempStr = df.format(temp.getNumericCellValue());
		//String tempStr = temp == null ? "" : temp.toString();
		retreatNums.add(tempStr);
		count++;// 统计解析行数
	    }
	} catch (IOException e) {
	    System.out.println("出错的行数: " + count);
	    e.printStackTrace();
	}
	System.out.println("退保个数 ：" + retreatNums.size());
	StringBuilder sb = new StringBuilder();
	for (String string : retreatNums) {
	    sb.append(string).append(",");
	}
	System.out.println(sb.toString());
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
 