package com.jeffy.sqlautoreview.vo;

import com.jeffy.sqlautoreview.model.ReviewResultModel;

/**  
 * ReviewResult Vo对象
 * @ClassName: ReviewResultVo    
 * @author 陈剑飞    
 * @date 2016年10月12日 下午6:23:55    
 * @version  v 1.0    
 */
public class ReviewResultVo extends ReviewResultModel{
	private static final long serialVersionUID = -8526082894323900347L;
	private String projectName;// 项目名称
	private String projectChName;// 项目中文名
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectChName() {
		return projectChName;
	}
	public void setProjectChName(String projectChName) {
		this.projectChName = projectChName;
	}
}
