package com.jeffy.sqlautoreview.core;

import java.util.HashMap;


/**  
 * json返回对象 
 * @ClassName: JsonResponse    
 * @author 陈剑飞    
 * @date 2016年8月20日 上午10:54:23    
 * @version  v 1.0    
 */
public class JsonResponse extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 916130971290399081L;
	/**
	 * 成功标识
	 */
	public static final String SUCCESS = "success";
	/**
	 * 失败标识
	 */
	public static final String FAIL = "fail";
	
	/**
	 * 登出返回字段
	 */
	public static final String LOGOUT_FIELD="logoutFlag";

	public JsonResponse() {
	}

	public JsonResponse(String result, String msg) {
		this.put("result", result);
		this.put("msg", msg);
	}

	public String getResult() {
		return this.get("result").toString();
	}

	public void setResult(String result) {
		this.put("result", result);
	}

	public String getMsg() {
		return this.get("msg").toString();
	}

	public void setMsg(String msg) {
		this.put("msg", msg);
	}
}
