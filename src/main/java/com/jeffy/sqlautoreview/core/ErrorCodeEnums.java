package com.jeffy.sqlautoreview.core;

/**  
 * 错误码定义   
 * @ClassName: ErrorCodeEnums    
 * @author 陈剑飞    
 * @date 2016年10月11日 下午2:45:22    
 * @version  v 1.0    
 */
public enum ErrorCodeEnums {
	NOT_HAVE_RECORD("NOT_HAVE_RECORD","记录没找到"),
	FIEL_NOTEXIST("FIEL_NOTEXIST","文件没找到"),
	REVIEW_DB_FAIL("REVIEW_DB_FAIL","请检查数据库信息配置是否正确");
	/**
     * 错误码
     */
    private String code;
    /**
     * 描述
     */
    private String msg;
    
    private ErrorCodeEnums(String code,String msg){
        this.code = code;
        this.msg = msg;
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
