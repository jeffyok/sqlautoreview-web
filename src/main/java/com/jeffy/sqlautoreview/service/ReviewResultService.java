package com.jeffy.sqlautoreview.service;

import java.util.List;

import com.jeffy.sqlautoreview.base.BaseService;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.model.ReviewResultModel;
import com.jeffy.sqlautoreview.vo.ReviewResultVo;

/**  
 * review结果服务接口   
 * @ClassName: ReviewResultService    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:30:57    
 * @version  v 1.0    
 */
public interface ReviewResultService extends BaseService<ReviewResultModel>{
	/**
	 * 根据项目ID删除review 结果 
	 * @author 陈剑飞    
	 * @Title: deleteByProjectId    
	 * @param projectId
	 * @Return: int 返回值
	 */
	int deleteByProjectId(Integer projectId) throws AppException;
	
	/**
	 * 根据条件查询ReviewResult vo 列表 
	 * @author 陈剑飞    
	 * @Title: queryReviewResultVoList    
	 * @param model 条件对象
	 * @param pager 分页对象
	 * @Return: List<ReviewResultVo> 返回值
	 */
	List<ReviewResultVo> queryReviewResultVoList(ReviewResultModel model,Pager pager) throws AppException;
}
