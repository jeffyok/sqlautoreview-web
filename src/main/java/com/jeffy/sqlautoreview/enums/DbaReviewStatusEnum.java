package com.jeffy.sqlautoreview.enums;


/**  
 * dba review 状态   
 * @ClassName: DbaReviewStatusEnum    
 * @author 陈剑飞    
 * @date 2016年10月17日 下午2:32:10    
 * @version  v 1.0    
 */
public enum DbaReviewStatusEnum {
	UN_AUDIT("0","未审核"),
	PASS("1","通过"),
	NOPASS("2","不通过");
	
	/**
	 * 类型
	 */
	private String status;
	/**
	 * 描述
	 */
	private String desc;
	
	private DbaReviewStatusEnum(String status,String desc){
		this.status = status;
		this.desc = desc;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 根据类型得到枚举类型对象
	 * @param status
	 * @return
	 */
	public static DbaReviewStatusEnum getDbaReviewStatus(String status) {
		for (DbaReviewStatusEnum bbaReviewStatus : DbaReviewStatusEnum.values()) {
			if (bbaReviewStatus.getStatus().equals(status)) {
				return bbaReviewStatus;
			}
		}
		return null;
	}
}
