package com.jeffy.sqlautoreview.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 错误对象
 * @ClassName: ApiError    
 * @author 陈剑飞    
 * @date 2019年4月27日 上午11:25:21    
 * @version  v 1.0
 */
public class ApiError implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Map<String, Object> values = new HashMap<>();

	public Map<String, Object> getValues() {
		return values;
	}
	public Integer getHttpCode() {
		return (Integer) values.get("httpCode");
	}
	public void setHttpCode(int httpCode) {
		values.put("httpCode", httpCode);
	}
	public String getCode() {
		return (String) values.get("code");
	}
	public void setCode(String code) {
		values.put("code", code);
	}
	public String getMsg() {
		return (String) values.get("msg");
	}
	public void setMsg(String msg) {
		values.put("msg", msg);
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getData() {
		Map<String, Object> data = (Map<String, Object>) values.get("data");
		if (data == null) {
			data = new HashMap<>();
			values.put("data", data);
		}
		return data;
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getErrors() {
		List<Map<String, Object>> errors = (List<Map<String, Object>>) values.get("errors");
		if (errors == null) {
			errors = new ArrayList<>();
			values.put("errors", errors);
		}
		return errors;
	}
	
	public ApiError() {}
	public ApiError(String code, String msg) {
		setCode(code);
		setMsg(msg);
	}
	public ApiError(Integer httpCode, String code, String msg) {
		if (httpCode != null) setHttpCode(httpCode);
		setCode(code);
		setMsg(msg);
	}
	public ApiError(Exception ex) {
		String msg = ex.getMessage();
		if (StringUtils.isEmpty(msg)) msg = ex.toString();
		setMsg(msg);
		if (ex instanceof IllegalArgumentException || ex instanceof MissingServletRequestParameterException) {
			setHttpCode(400);
		} else if (ex instanceof IllegalStateException) {
			setHttpCode(503);
		}
	}
}
