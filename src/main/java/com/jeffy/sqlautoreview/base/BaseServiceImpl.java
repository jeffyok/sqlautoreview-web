package com.jeffy.sqlautoreview.base;

import java.util.List;

import com.jeffy.sqlautoreview.base.pagination.Pager;

/**  
 * 基础服务实现类   
 * @ClassName: BaseServiceImpl    
 * @author 陈剑飞    
 * @date 2016年8月17日 下午10:43:31    
 * @version  v 1.0    
 */
public abstract class BaseServiceImpl<T> implements BaseService<T>{
	/**
	 * 获取mapper
	 * @author 陈剑飞    
	 * @Title: getMapper    
	 * @return 
	 * @Return: BaseMapper 返回值
	 */
	protected abstract BaseMapper getMapper();

	@Override
	public int add(T model) throws ApiException {
		return getMapper().add(model);
	}

	@Override
	public int addBatch(List<T> list) throws ApiException {
		return getMapper().addBatch(list);
	}

	@Override
	public int update(T model) throws ApiException {
		return getMapper().update(model);
	}

	@Override
	public int updateBySelective(T model) throws ApiException {
		return getMapper().updateBySelective(model);
	}

	@Override
	public int deleteById(Object id) throws ApiException {
		return getMapper().deleteById(id);
	}

	@Override
	public int queryCount(T model) throws ApiException {
		return getMapper().queryCount(model);
	}

	@Override
	public List<T> queryList(T model, Pager pager) throws ApiException {
		return getMapper().queryList(model, pager);
	}

	@Override
	public T getById(Object id) throws ApiException {
		return getMapper().getById(id);
	}
}
