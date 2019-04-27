package com.jeffy.sqlautoreview.enums;

/**  
 * sql类型枚举类    
 * @ClassName: SqlTypeEnum    
 * @author 陈剑飞    
 * @date 2016年10月20日 下午2:07:22    
 * @version  v 1.0    
 */
public enum SqlTypeEnum {
	INSERT("insert","insert语句"),
	DELETE("delete","delete语句"),
	UPDETE("update","update语句"),
	SELETE("select","select语句");
	
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String desc;
	
	private SqlTypeEnum(String type,String desc){
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 根据类型得到枚举类型对象
	 * @param sqlType sql类型
	 * @return SqlTypeEnum
	 */
	public static SqlTypeEnum getSqlType(String sqlType) {
		for (SqlTypeEnum sqlTypeEnum : SqlTypeEnum.values()) {
			if (sqlTypeEnum.getType().equals(sqlType)) {
				return sqlTypeEnum;
			}
		}
		return null;
	}
}
