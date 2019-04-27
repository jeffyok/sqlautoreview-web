package com.jeffy.sqlautoreview.enums;

/**  
 * review 状态枚举类  
 * @ClassName: ReviewStatusEnum    
 * @author 陈剑飞    
 * @date 2016年10月17日 下午2:49:32    
 * @version  v 1.0    
 */
public enum ReviewStatusEnum {
	UN_REVIEW("0","未review"),
	REVIEWED("1","已review"),
	REVIEWED_ERR("2","暂不支持");
	
	/**
	 * 类型
	 */
	private String status;
	/**
	 * 描述
	 */
	private String desc;
	
	private ReviewStatusEnum(String status,String desc){
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
	public static ReviewStatusEnum getReviewStatus(String status) {
		for (ReviewStatusEnum reviewStatus : ReviewStatusEnum.values()) {
			if (reviewStatus.getStatus().equals(status)) {
				return reviewStatus;
			}
		}
		return null;
	}
}
