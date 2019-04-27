package com.jeffy.sqlautoreview.vo;

import com.jeffy.sqlautoreview.model.SqlreviewModel;

/**  
 * sql review vo 对象
 * @ClassName: SqlreviewVo    
 * @author 陈剑飞    
 * @date 2016年10月12日 下午5:08:18    
 * @version  v 1.0    
 */
public class SqlreviewVo extends SqlreviewModel{
	private static final long serialVersionUID = 3097612903029947321L;
	private String projectName;// 项目名称
	private String projectChName;// 项目中文名
	private String fileName;// 文件名
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
