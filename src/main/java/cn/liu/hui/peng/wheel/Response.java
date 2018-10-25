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


/**
 * <p>
 * 
 * 结果泛型实体
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月14日 下午4:59:40
 * @version      
 */
public class Response<T> {

    private T result;

    private String msg;

    private String status = "00000";
    
    private boolean success = Boolean.TRUE;
    
    private int totalRows;
    
    
    public static class ResponseBuilder<T> {

	private T result;

	private String msg;

	private String status = "00000";

	private boolean success = Boolean.TRUE;
	
	public ResponseBuilder<T> fail() {
	    this.msg = "";
	    this.status = "-1";
	    this.success = Boolean.FALSE;
	    return this;
	}
    }

    public Response() {
    }

    public Response(T result) {
        this.result = result;
    }


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        if (result != null) {
            this.result = result;
        }
    }

    
    public String getMsg() {
        return msg;
    }

    
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    public boolean isSuccess() {
        return success;
    }

    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static <T> Response<T> of(String msg, String status) {
        Response<T> response = new Response<T>();
        response.setMsg(msg);
        response.setStatus(status);
        return response;
    }
}
 