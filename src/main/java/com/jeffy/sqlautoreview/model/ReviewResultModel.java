package com.jeffy.sqlautoreview.model;

import com.jeffy.sqlautoreview.base.BaseModel;

/**  
 * review 结果model
 * @ClassName: ReviewResultModel    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:24:27    
 * @version  v 1.0    
 */
public class ReviewResultModel extends BaseModel {
	private static final long serialVersionUID = 3457325666637765374L;
	private Integer id;// 主键	private Integer projectId;// 项目ID	private String tablename;// 表名	private String realTablename;// 真实表名	private String existIndexes;// 已存在索引	private String newIndexes;// 新索引	private String mergeResult;// 合并结果	private java.util.Date createTime;// 创建时间	private java.util.Date updateTime;// 修改时间	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getProjectId() {	    return this.projectId;	}	public void setProjectId(Integer projectId) {	    this.projectId=projectId;	}	public String getTablename() {	    return this.tablename;	}	public void setTablename(String tablename) {	    this.tablename=tablename;	}	public String getRealTablename() {	    return this.realTablename;	}	public void setRealTablename(String realTablename) {	    this.realTablename=realTablename;	}	public String getExistIndexes() {	    return this.existIndexes;	}	public void setExistIndexes(String existIndexes) {	    this.existIndexes=existIndexes;	}	public String getNewIndexes() {	    return this.newIndexes;	}	public void setNewIndexes(String newIndexes) {	    this.newIndexes=newIndexes;	}	public String getMergeResult() {	    return this.mergeResult;	}	public void setMergeResult(String mergeResult) {	    this.mergeResult=mergeResult;	}	public java.util.Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(java.util.Date createTime) {	    this.createTime=createTime;	}	public java.util.Date getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(java.util.Date updateTime) {	    this.updateTime=updateTime;	}
}
