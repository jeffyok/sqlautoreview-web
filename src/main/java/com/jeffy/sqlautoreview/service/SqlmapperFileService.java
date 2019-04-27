package com.jeffy.sqlautoreview.service;

import java.util.List;

import com.jeffy.sqlautoreview.base.BaseService;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.model.SqlmapperFileModel;
import com.jeffy.sqlautoreview.vo.SqlmapperFileVo;

/**  
 * sqlmapper文件服务接口
 * @ClassName: SqlmapperFileService    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:07:49    
 * @version  v 1.0    
 */
public interface SqlmapperFileService extends BaseService<SqlmapperFileModel> {
	/**
	 * 根据项目ID扫描mapper文件
	 * @author 陈剑飞    
	 * @Title: scanMapperFile    
	 * @param projectId 
	 * @Return: void 返回值
	 */
	void scanMapperFile(Integer projectId)throws AppException;
	
	/**
	 * 根据条件查询sql mapper vo 列表 
	 * @author 陈剑飞    
	 * @Title: querySqlmapperFileVoList    
	 * @param model 条件对象
	 * @param pager 分页对象
	 * @Return: List<SqlmapperFileVo> 返回值
	 */
	List<SqlmapperFileVo> querySqlmapperFileVoList(SqlmapperFileModel model,Pager pager)throws AppException;
}
