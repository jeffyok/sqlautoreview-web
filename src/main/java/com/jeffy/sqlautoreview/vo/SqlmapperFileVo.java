package com.jeffy.sqlautoreview.vo;

import com.jeffy.sqlautoreview.model.SqlmapperFileModel;

/**  
 * sql mapper 文件 vo对象 
 * @ClassName: SqlmapperFileVo    
 * @author 陈剑飞    
 * @date 2016年10月12日 下午5:06:25    
 * @version  v 1.0    
 */
public class SqlmapperFileVo extends SqlmapperFileModel{
	/**
	 *@Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)    
	 */
	
	private static final long serialVersionUID = 5666876615407360579L;
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
