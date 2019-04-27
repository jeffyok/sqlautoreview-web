package com.jeffy.sqlautoreview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeffy.sqlautoreview.base.BaseMapper;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.model.SqlmapperFileModel;
import com.jeffy.sqlautoreview.vo.SqlmapperFileVo;


/**  
 * sqlmapper文件mapper对象  
 * @ClassName: SqlmapperFileMapper    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:07:02    
 * @version  v 1.0    
 */
public interface SqlmapperFileMapper extends BaseMapper {
	/**
	 * 根据项目ID查找sql mapper File 列表
	 * @author 陈剑飞    
	 * @Title: getSqlmapperFileListByProjectId    
	 * @param projectId
	 * @Return: List<SqlmapperFileModel> 返回值
	 */
	List<SqlmapperFileModel> getSqlmapperFileListByProjectId(@Param("projectId")Integer projectId);	
	
	/**
	 * 根据项目ID删除sql mapper文件 
	 * @author 陈剑飞    
	 * @Title: deleteByProjectId    
	 * @param projectId 项目ID
	 * @Return: int 返回值
	 */
	int deleteByProjectId(Integer projectId);
	
	/**
	 * 根据条件查询sql mapper vo 列表 
	 * @author 陈剑飞    
	 * @Title: querySqlmapperFileVoList    
	 * @param model 条件对象
	 * @param pager 分页对象
	 * @Return: List<SqlmapperFileVo> 返回值
	 */
	List<SqlmapperFileVo> querySqlmapperFileVoList(@Param("model")SqlmapperFileModel model, @Param("pager") Pager pager);
	
	/**
	 * 根据projectId和fileName查询sql mapper
	 * @author 陈剑飞    
	 * @Title: getSqlmapperFileModelByProjectIdAndfileName    
	 * @param projectId 项目ID
	 * @param fileName  文件名
	 * @Return: SqlmapperFileModel 返回值
	 */
	SqlmapperFileModel getSqlmapperFileModelByProjectIdAndfileName(@Param("projectId")Integer projectId,@Param("fileName")String fileName);
	
	/**
	 * 根据projectId和fileName更新
	 * @author 陈剑飞    
	 * @Title: updateSqlmapperFileModelByProjectIdAndfileName    
	 * @param model sqlmapper 模型对象
	 * @Return: int 返回值
	 */
	int updateSqlmapperFileModelByProjectIdAndfileName(SqlmapperFileModel model); 
}
