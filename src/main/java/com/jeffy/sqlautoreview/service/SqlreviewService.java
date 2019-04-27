package com.jeffy.sqlautoreview.service;

import java.util.List;

import com.jeffy.sqlautoreview.base.BaseService;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.model.SqlreviewModel;
import com.jeffy.sqlautoreview.vo.SqlreviewVo;

/**  
 * sqlreview服务接口    
 * @ClassName: SqlreviewService    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:17:53    
 * @version  v 1.0    
 */
public interface SqlreviewService extends BaseService<SqlreviewModel>{
	
	/**
	 * 自动review
	 * @author 陈剑飞    
	 * @Title: autoReview    
	 * @param projectId 项目ID
	 * @throws Exception 
	 * @Return: void 返回值
	 */
	void review(Integer projectId) throws Exception;
	
	/**
	 * 根据条件查询Sql review vo 列表 
	 * @author 陈剑飞    
	 * @Title: querySqlreviewVoList    
	 * @param model 条件对象
	 * @param pager 分页对象
	 * @Return: List<SqlreviewVo> 返回值
	 */
	List<SqlreviewVo> querySqlreviewVoList(SqlreviewVo vo,Pager pager) throws AppException;
	
	/**
	 * 根据条件查询sql review 条数 
	 * @author 陈剑飞    
	 * @Title: querySqlreviewVoCount    
	 * @param vo
	 * @return 
	 * @Return: int 返回值
	 */
	int querySqlreviewVoCount(SqlreviewVo vo) throws AppException;
	
}
