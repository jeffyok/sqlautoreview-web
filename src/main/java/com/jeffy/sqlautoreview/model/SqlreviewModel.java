package com.jeffy.sqlautoreview.model;

import com.jeffy.sqlautoreview.base.BaseModel;

/**  
 * sqlreview model    
 * @ClassName: SqlreviewModel    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:16:18    
 * @version  v 1.0    
 */
public class SqlreviewModel extends BaseModel {
	private static final long serialVersionUID = -6025292167011382969L;
	private Integer id;// 主键
	private Integer projectId;// 项目ID
	private Integer mapperFileId;// 文件ID
	private String javaClassId;// mapper方法名
	private String sqlXml;// mapper sql
	private String sqlComment;// mapper备注
	private String realSql;// 真实sql
	private String realSqlHash;// 真实sql hash
	private String tableName;// 表名
	private Integer status;// 状态 0 未review 1已review  2review出错
	private String autoReviewErr;// 自动检查错误信息
	private String autoReviewTip;// 自动检查提示
	private java.util.Date autoReviewTime;// 自动检查时间
	private String sqlAutoIndex;// sql自动索引
	private java.util.Date dbaReviewTime;// dba检查时间
	private String sqlDbaIndex;// dba索引
	private String indexFlag;// 索引标记,1已标记 0未标记
	private String sqlType;// sql类型，insert，select，update，delete
	private String dbaAdvice;// dba建议
	private String dbaReviewStatus;// dba 检查状态 0未审核 1通过 2不通过
	private java.util.Date createTime;// 创建时间
	private java.util.Date updateTime;// 修改时间
	public Integer getId() {
	    return this.id;
	}
	public void setId(Integer id) {
	    this.id=id;
	}
	public Integer getProjectId() {
	    return this.projectId;
	}
	public void setProjectId(Integer projectId) {
	    this.projectId=projectId;
	}
	public Integer getMapperFileId() {
	    return this.mapperFileId;
	}
	public void setMapperFileId(Integer mapperFileId) {
	    this.mapperFileId=mapperFileId;
	}
	public String getJavaClassId() {
	    return this.javaClassId;
	}
	public void setJavaClassId(String javaClassId) {
	    this.javaClassId=javaClassId;
	}
	public String getSqlXml() {
	    return this.sqlXml;
	}
	public void setSqlXml(String sqlXml) {
	    this.sqlXml=sqlXml;
	}
	public String getSqlComment() {
	    return this.sqlComment;
	}
	public void setSqlComment(String sqlComment) {
	    this.sqlComment=sqlComment;
	}
	public String getRealSql() {
	    return this.realSql;
	}
	public void setRealSql(String realSql) {
	    this.realSql=realSql;
	}
	public String getRealSqlHash() {
	    return this.realSqlHash;
	}
	public void setRealSqlHash(String realSqlHash) {
	    this.realSqlHash=realSqlHash;
	}
	public String getTableName() {
	    return this.tableName;
	}
	public void setTableName(String tableName) {
	    this.tableName=tableName;
	}
	public Integer getStatus() {
	    return this.status;
	}
	public void setStatus(Integer status) {
	    this.status=status;
	}
	public String getAutoReviewErr() {
	    return this.autoReviewErr;
	}
	public void setAutoReviewErr(String autoReviewErr) {
	    this.autoReviewErr=autoReviewErr;
	}
	public String getAutoReviewTip() {
	    return this.autoReviewTip;
	}
	public void setAutoReviewTip(String autoReviewTip) {
	    this.autoReviewTip=autoReviewTip;
	}
	public java.util.Date getAutoReviewTime() {
	    return this.autoReviewTime;
	}
	public void setAutoReviewTime(java.util.Date autoReviewTime) {
	    this.autoReviewTime=autoReviewTime;
	}
	public String getSqlAutoIndex() {
	    return this.sqlAutoIndex;
	}
	public void setSqlAutoIndex(String sqlAutoIndex) {
	    this.sqlAutoIndex=sqlAutoIndex;
	}
	public java.util.Date getDbaReviewTime() {
	    return this.dbaReviewTime;
	}
	public void setDbaReviewTime(java.util.Date dbaReviewTime) {
	    this.dbaReviewTime=dbaReviewTime;
	}
	public String getSqlDbaIndex() {
	    return this.sqlDbaIndex;
	}
	public void setSqlDbaIndex(String sqlDbaIndex) {
	    this.sqlDbaIndex=sqlDbaIndex;
	}
	public String getIndexFlag() {
	    return this.indexFlag;
	}
	public void setIndexFlag(String indexFlag) {
	    this.indexFlag=indexFlag;
	}
	public String getSqlType() {
	    return this.sqlType;
	}
	public void setSqlType(String sqlType) {
	    this.sqlType=sqlType;
	}
	public String getDbaAdvice() {
	    return this.dbaAdvice;
	}
	public void setDbaAdvice(String dbaAdvice) {
	    this.dbaAdvice=dbaAdvice;
	}
	public String getDbaReviewStatus() {
	    return this.dbaReviewStatus;
	}
	public void setDbaReviewStatus(String dbaReviewStatus) {
	    this.dbaReviewStatus=dbaReviewStatus;
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
