package com.jeffy.sqlautoreview.base;

/**  
 * 异常对象   
 * @ClassName: ApiException    
 * @author 陈剑飞    
 * @date 2019年4月27日 上午11:25:31    
 * @version  v 1.0    
 */
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private ApiError error;

	public ApiError getError() {
		return error;
	}
	

	public ApiException() {}
	public ApiException(ApiError error) {
		super(error.getMsg());
		this.error = error;
	}
	public ApiException(Exception ex) {
		super(ex.getMessage(), ex);
		error = new ApiError(ex);
	}
	public ApiException(String code, String msg) {
		this(new ApiError(code, msg));
	}
	public ApiException(int httpCode, String code, String msg) {
		this(new ApiError(httpCode, code, msg));
	}
}
