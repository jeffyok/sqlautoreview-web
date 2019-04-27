package com.jeffy.sqlautoreview.mapper;

import org.apache.ibatis.annotations.Param;

import com.jeffy.sqlautoreview.base.BaseMapper;
import com.jeffy.sqlautoreview.model.ProjectModel;

/**  
 * 项目mapper 
 * @ClassName: ProjectMapper    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午2:55:46    
 * @version  v 1.0    
 */
public interface ProjectMapper extends BaseMapper {
	/**
	 * 判断项目是否存在
	 * @author 陈剑飞    
	 * @Title: isExistsProject    
	 * @param model 项目对象
	 * @Return: int 返回值
	 */
	int isExistsProject(@Param("model")ProjectModel model);
}
