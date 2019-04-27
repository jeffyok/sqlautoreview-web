package com.jeffy.sqlautoreview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeffy.sqlautoreview.base.BaseMapper;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.model.SqlreviewModel;
import com.jeffy.sqlautoreview.vo.SqlreviewVo;

/**  
 * sqlreview mapper  
 * @ClassName: SqlreviewMapper    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:17:05    
 * @version  v 1.0    
 */
public interface SqlreviewMapper extends BaseMapper {
	/**
	 * 根据项目查询所有检查sql
	 * @author 陈剑飞    
	 * @Title: getAllSqlReviewByProjectId    
	 * @param projectId 项目ID
	 * @param status 检查状态
	 * @Return: List<SqlreviewModel> 返回值
	 */
	List<SqlreviewModel> getAllSqlReviewByProjectId(@Param("projectId")Integer projectId,@Param("status")Integer status);
	
	/**
	 * 根据项目ID删除sql review
	 * @author 陈剑飞    
	 * @Title: deleteByProjectId    
	 * @param projectId 项目ID
	 * @Return: int 返回值
	 */
	int deleteByProjectId(Integer projectId);
	
	/**
	 * 根据条件查询Sql review vo 列表 
	 * @author 陈剑飞    
	 * @Title: querySqlreviewVoList    
	 * @param vo 条件对象
	 * @param pager 分页对象
	 * @Return: List<SqlreviewVo> 返回值
	 */
	List<SqlreviewVo> querySqlreviewVoList(@Param("model")SqlreviewVo vo, @Param("pager") Pager pager);
	
	/**
	 * 根据条件查询sql review 条数 
	 * @author 陈剑飞    
	 * @Title: querySqlreviewVoCount    
	 * @param vo 条件对象
	 * @Return: int 返回值
	 */
	int querySqlreviewVoCount(@Param("model")SqlreviewVo vo);
}
