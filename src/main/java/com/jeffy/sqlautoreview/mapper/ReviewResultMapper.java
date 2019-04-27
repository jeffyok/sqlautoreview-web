package com.jeffy.sqlautoreview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeffy.sqlautoreview.base.BaseMapper;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.model.ReviewResultModel;
import com.jeffy.sqlautoreview.vo.ReviewResultVo;


/**  
 * review 结果mapper 
 * @ClassName: ReviewResultMapper    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:25:28    
 * @version  v 1.0    
 */
public interface ReviewResultMapper extends BaseMapper {
	/**
	 * 根据项目ID删除review 结果 
	 * @author 陈剑飞    
	 * @Title: deleteByProjectId    
	 * @param projectId
	 * @Return: int 返回值
	 */
	int deleteByProjectId(@Param("projectId")Integer projectId);
	
	/**
	 * 根据条件查询ReviewResult vo 列表 
	 * @author 陈剑飞    
	 * @Title: queryReviewResultVoList    
	 * @param model 条件对象
	 * @param pager 分页对象
	 * @Return: List<ReviewResultVo> 返回值
	 */
	List<ReviewResultVo> queryReviewResultVoList(@Param("model")ReviewResultModel model, @Param("pager") Pager pager);
}
