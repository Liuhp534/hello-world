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

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author	hz16092620 
 * @date	2018年3月12日 上午11:37:07
 * @version      
 */
public class CallRecordDTO {
    
    /**
     * 日期转字符串 默认格式：YYYY_MM_DD
     * @param dateStr
     * @return
     *  
     * @author	hz16092620 
     * @date	2017年9月13日 上午10:32:11
     * @version      
     */ 
    public static String date2String() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	try {
	    return sdf.format(new Date());
	} catch (Exception e) {
	}
	return "";
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
    public static String date2StringAdd() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	long lDate = new Date().getTime() + 10*60*1000;
	Date date = new Date(lDate);
	try {
	    return sdf.format(date);
	} catch (Exception e) {
	}
	return "";
    }
    
    /**0*/
    private String callResult;
    /**1*/
    private String dialTime;
    /**2*/
    private String ringTime;
    /**3*/
    private String  answerTime;
    /**4*/
    private String  hangupTime;
    /**5*/
    private String  seatId;
    /**6*/
    private String  seatNum;
    /**7*/
    private String  seatName;
    /**8*/
    private String  seatType;
    /**9*/
    private String  seatPhone;
    /**10*/
    private String  partnerId;
    /**11*/
    private String  partnerName;
    /**12*/
    private String  partnerPhone;
    /**13*/
    private String  firstSolve;
    /**14*/
    private String  recordResult;
    /**15*/
    private String  recordDesc;
    
    public String getCallResult() {
        return callResult;
    }
    
    public void setCallResult(String callResult) {
        this.callResult = callResult;
    }
    
    public String getDialTime() {
        return date2String();
    }
    
    public void setDialTime(String dialTime) {
        this.dialTime = dialTime;
    }
    
    public String getRingTime() {
        return date2String();
    }
    
    public void setRingTime(String ringTime) {
        this.ringTime = ringTime;
    }
    
    public String getAnswerTime() {
        return date2String();
    }
    
    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }
    
    public String getHangupTime() {
        return date2StringAdd();
    }
    
    public void setHangupTime(String hangupTime) {
        this.hangupTime = hangupTime;
    }
    
    public String getSeatId() {
        return seatId;
    }
    
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
    
    public String getSeatNum() {
        return seatNum;
    }
    
    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }
    
    public String getSeatName() {
        return seatName;
    }
    
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
    
    public String getSeatType() {
        return seatType;
    }
    
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
    
    public String getSeatPhone() {
        return seatPhone;
    }
    
    public void setSeatPhone(String seatPhone) {
        this.seatPhone = seatPhone;
    }
    
    public String getPartnerId() {
        return partnerId;
    }
    
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    
    public String getPartnerName() {
        return partnerName;
    }
    
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
    
    public String getPartnerPhone() {
        return partnerPhone;
    }
    
    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone;
    }
    
    public String getFirstSolve() {
        return firstSolve;
    }
    
    public void setFirstSolve(String firstSolve) {
        this.firstSolve = firstSolve;
    }
    
    public String getRecordResult() {
        return recordResult;
    }
    
    public void setRecordResult(String recordResult) {
        this.recordResult = recordResult;
    }
    
    public String getRecordDesc() {
        return recordDesc;
    }
    
    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }
    
    
    
    

}
 