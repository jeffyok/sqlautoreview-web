package com.jeffy.sqlautoreview.service;

import com.jeffy.sqlautoreview.base.BaseService;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.model.ProjectModel;

/**  
 * 项目服务接口   
 * @ClassName: ProjectService    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午2:58:57    
 * @version  v 1.0    
 */
public interface ProjectService  extends BaseService<ProjectModel> {
	/**
	 * 判断项目是否存在
	 * @author 陈剑飞    
	 * @Title: isExistsProject    
	 * @param model 项目对象
	 * @Return: boolean 返回值
	 */
	boolean isExistsProject(ProjectModel model) throws AppException;
}
