package com.jeffy.sqlautoreview.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeffy.sqlautoreview.base.BaseMapper;
import com.jeffy.sqlautoreview.base.BaseServiceImpl;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.mapper.ProjectMapper;
import com.jeffy.sqlautoreview.mapper.ReviewResultMapper;
import com.jeffy.sqlautoreview.mapper.SqlmapperFileMapper;
import com.jeffy.sqlautoreview.mapper.SqlreviewMapper;
import com.jeffy.sqlautoreview.model.ProjectModel;
import com.jeffy.sqlautoreview.service.ProjectService;

/**  
 * 项目服务实现
 * @ClassName: ProjectServiceImpl    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:00:35    
 * @version  v 1.0    
 */
@Service
@Transactional
public class ProjectServiceImpl extends BaseServiceImpl<ProjectModel> implements ProjectService{
	@Autowired
	private ProjectMapper mapper;
	@Autowired
	private SqlreviewMapper sqlreviewMapper;
	@Autowired
	private ReviewResultMapper reviewResultMapper;
	@Autowired
	private SqlmapperFileMapper sqlmapperFileMapper;
	
	@Override
	protected BaseMapper getMapper() {
		return mapper;
	}

	@Override
	public int deleteById(Object projectId) throws AppException {
		//删除关联数据
		sqlmapperFileMapper.deleteByProjectId((Integer)projectId);
		sqlreviewMapper.deleteByProjectId((Integer)projectId);
		reviewResultMapper.deleteByProjectId((Integer)projectId);
		//删除项目
		int result = super.deleteById(projectId);
		return result;
	}

	@Override
	public boolean isExistsProject(ProjectModel model) throws AppException {
		return mapper.isExistsProject(model)>0?true:false;
	}
	
	
}
