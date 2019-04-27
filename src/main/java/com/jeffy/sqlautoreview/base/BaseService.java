package com.jeffy.sqlautoreview.base;

import java.util.List;

import com.jeffy.sqlautoreview.base.pagination.Pager;

/**  
 * 基础服务接口  
 * @ClassName: BaseService    
 * @author 陈剑飞    
 * @date 2016年8月17日 下午11:14:39    
 * @version  v 1.0
 * @param <T>    
 */
public interface BaseService<T> {
	/**
	 * 新增对象
	 * 
	 * @param model 实体对象
	 */
	int add(T model) throws ApiException;

	/**
	 * 批量插入记录
	 * 
	 * @param list 实体集合
	 * @return int 返回记录数
	 */
	int addBatch(List<T> list) throws ApiException;

	/**
	 * 更新实体
	 * 
	 * @param model 实体对象
	 */
	int update(T model) throws ApiException;

	/**
	 * 根据选择条件更新实体
	 * 
	 * @param model 实体对象
	 */
	int updateBySelective(T model) throws ApiException;

	/**
	 * 删除对象
	 * 
	 * @param id 对象ID
	 */
	int deleteById(Object id) throws ApiException;

	/**
	 * 获取记录数
	 * 
	 * @param model 模型对象
	 * @return int 总记录数
	 */
	int queryCount(T model) throws ApiException;

	/**
	 * 条件查询
	 * 
	 * @param model 条件对象
	 * @param pager 分页对象，传了就分页，不传不分页
	 * @return List 返回列表
	 */
	List<T> queryList(T model, Pager pager) throws ApiException;

	/**
	 * 根据主键ID查找对象
	 * 
	 * @param id 主键ID
	 * @return 对象
	 */
	T getById(Object id) throws ApiException;
}
