package com.jeffy.sqlautoreview.core;

/**  
 * 业务异常   
 * @ClassName: AppException    
 * @author 陈剑飞    
 * @date 2016年10月11日 上午10:49:37    
 * @version  v 1.0    
 */
public class AppException extends RuntimeException{
	private static final long serialVersionUID = -8373603972389013843L;
	private String code;//错误码
	private String msg;//错误描述

	public AppException(String code,String msg){
		super(msg);
		this.code = code;
		this.msg = msg;
	}
	
	public AppException(String code,String msg,Throwable e){
		super(msg,e);
		this.code = code;
		this.msg = msg;
	}
	
	public AppException(String msg){
		super(msg);
		this.msg = msg;
	}
	
	public AppException(ErrorCodeEnums errorCode){
		super(errorCode.getMsg());
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
	}
	
	public AppException(Throwable e){
		super(e);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
