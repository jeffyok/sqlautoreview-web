package com.jeffy.sqlautoreview.model;

import com.jeffy.sqlautoreview.base.BaseModel;

/**  
 * 项目model对象   
 * @ClassName: ProjectModel    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午2:55:15    
 * @version  v 1.0    
 */
public class ProjectModel extends BaseModel {
	private static final long serialVersionUID = 1922024668792876763L;
	private Integer projectId;// 项目ID
	private String projectName;// 项目名称
	private String projectChName;// 项目中文名
	private String projectDesc;// 项目描述
	private String dbIp;// 数据库ip
	private Integer dbPort;// 数据库端口
	private String dbName;// 数据库
	private String dbUser;// 数据库用户名
	private String dbPassword;// 数据库密码
	private String mapperRootPath;// mapper文件根路径
	private java.util.Date reviewTime;// review时间
	private String reviewFlag;// 是否review
	private Double score;// 得分
	private String createUser;// 创建人
	private String updateUser;// 修改人
	private java.util.Date createTime;// 创建时间
	private java.util.Date updateTime;// 修改时间
	public Integer getProjectId() {
	    return this.projectId;
	}
	public void setProjectId(Integer projectId) {
	    this.projectId=projectId;
	}
	public String getProjectName() {
	    return this.projectName;
	}
	public void setProjectName(String projectName) {
	    this.projectName=projectName;
	}
	public String getProjectChName() {
	    return this.projectChName;
	}
	public void setProjectChName(String projectChName) {
	    this.projectChName=projectChName;
	}
	public String getProjectDesc() {
	    return this.projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
	    this.projectDesc=projectDesc;
	}
	public String getDbIp() {
	    return this.dbIp;
	}
	public void setDbIp(String dbIp) {
	    this.dbIp=dbIp;
	}
	public Integer getDbPort() {
	    return this.dbPort;
	}
	public void setDbPort(Integer dbPort) {
	    this.dbPort=dbPort;
	}
	public String getDbName() {
	    return this.dbName;
	}
	public void setDbName(String dbName) {
	    this.dbName=dbName;
	}
	public String getDbUser() {
	    return this.dbUser;
	}
	public void setDbUser(String dbUser) {
	    this.dbUser=dbUser;
	}
	public String getDbPassword() {
	    return this.dbPassword;
	}
	public void setDbPassword(String dbPassword) {
	    this.dbPassword=dbPassword;
	}
	public String getMapperRootPath() {
	    return this.mapperRootPath;
	}
	public void setMapperRootPath(String mapperRootPath) {
	    this.mapperRootPath=mapperRootPath;
	}
	public java.util.Date getReviewTime() {
	    return this.reviewTime;
	}
	public void setReviewTime(java.util.Date reviewTime) {
	    this.reviewTime=reviewTime;
	}
	
	public String getReviewFlag() {
		return reviewFlag;
	}
	
	public void setReviewFlag(String reviewFlag) {
		this.reviewFlag = reviewFlag;
	}
	
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getCreateUser() {
	    return this.createUser;
	}
	public void setCreateUser(String createUser) {
	    this.createUser=createUser;
	}
	public String getUpdateUser() {
	    return this.updateUser;
	}
	public void setUpdateUser(String updateUser) {
	    this.updateUser=updateUser;
	}
	public java.util.Date getCreateTime() {
	    return this.createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
	    this.createTime=createTime;
	}
	public java.util.Date getUpdateTime() {
	    return this.updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
	    this.updateTime=updateTime;
	}
}
