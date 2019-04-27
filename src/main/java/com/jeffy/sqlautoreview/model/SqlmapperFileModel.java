package com.jeffy.sqlautoreview.model;

import com.jeffy.sqlautoreview.base.BaseModel;

/**  
 * sqlmapper 文件model    
 * @ClassName: SqlmapperFileModel    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:06:13    
 * @version  v 1.0    
 */
public class SqlmapperFileModel extends BaseModel {
	private static final long serialVersionUID = 6964132868156507465L;
	private Integer mapperFileId;// 文件ID	private Integer projectId;// 项目ID	private String mapperFilePath;// 文件路径,全路径	private String fileName;// 文件名	private java.util.Date scanTime;// 扫描时间	private String operateUser;// 操作人	private String updateUser;// 修改人	private String updateTime;// 修改时间	public Integer getMapperFileId() {	    return this.mapperFileId;	}	public void setMapperFileId(Integer mapperFileId) {	    this.mapperFileId=mapperFileId;	}	public Integer getProjectId() {	    return this.projectId;	}	public void setProjectId(Integer projectId) {	    this.projectId=projectId;	}	public String getMapperFilePath() {	    return this.mapperFilePath;	}	public void setMapperFilePath(String mapperFilePath) {	    this.mapperFilePath=mapperFilePath;	}	public String getFileName() {	    return this.fileName;	}	public void setFileName(String fileName) {	    this.fileName=fileName;	}	public java.util.Date getScanTime() {	    return this.scanTime;	}	public void setScanTime(java.util.Date scanTime) {	    this.scanTime=scanTime;	}	public String getOperateUser() {	    return this.operateUser;	}	public void setOperateUser(String operateUser) {	    this.operateUser=operateUser;	}	public String getUpdateUser() {	    return this.updateUser;	}	public void setUpdateUser(String updateUser) {	    this.updateUser=updateUser;	}	public String getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(String updateTime) {	    this.updateTime=updateTime;	}
}
