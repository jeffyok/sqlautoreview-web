package com.jeffy.sqlautoreview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeffy.sqlautoreview.base.BaseMapper;
import com.jeffy.sqlautoreview.base.BaseServiceImpl;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.mapper.ReviewResultMapper;
import com.jeffy.sqlautoreview.model.ReviewResultModel;
import com.jeffy.sqlautoreview.service.ReviewResultService;
import com.jeffy.sqlautoreview.vo.ReviewResultVo;


/**  
 * review结果服务实现
 * @ClassName: ReviewResultServiceImpl    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:32:28    
 * @version  v 1.0    
 */
@Service
@Transactional
public class ReviewResultServiceImpl extends BaseServiceImpl<ReviewResultModel> implements ReviewResultService{
	@Autowired
	private ReviewResultMapper mapper;
	@Override
	protected BaseMapper getMapper() {
		return mapper;
	}
	
	@Override
	public int deleteByProjectId(Integer projectId) throws AppException{
		return mapper.deleteByProjectId(projectId);
	}

	@Override
	public List<ReviewResultVo> queryReviewResultVoList (
			ReviewResultModel model, Pager pager) throws AppException{
		return mapper.queryReviewResultVoList(model, pager);
	}
	
	
}
