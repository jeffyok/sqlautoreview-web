package com.jeffy.sqlautoreview.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeffy.sqlautoreview.base.pagination.Pager;

/**
 * 
 * 基础mapper
 * 
 * @ClassName: BaseMapper
 * @author 陈剑飞
 * @date 2016年2月24日 上午10:20:38
 * @version
 */
public interface BaseMapper
{
    
    /**
     * 新增对象
     * 
     * @param t 实体对象
     */
    <T> int add(T model);
    
    /**
     * 批量插入记录
     * 
     * @param list
     * @return int 返回记录数
     */
    <T> int addBatch(List<T> list);
    
    /**
     * 更新实体
     * 
     * @param t 实体对象
     */
    <T> int update(T model);
    
    /**
     * 根据选择条件更新实体
     * 
     * @param t 实体对象
     */
    <T> int updateBySelective(T model);
    
    /**
     * 根据主键ID删除对象
     * 
     * @param id 对象ID
     */
    int deleteById(Object id);
    
    /**
     * 获取记录数
     * 
     * @param model 模型对象
     * @return int 总记录数
     */
    <T> int queryCount(@Param("model") T model);
    
    /**
     * 条件查询
     * 
     * @param model 条件对象
     * @param pager 分页对象，传了就分页，不传不分页
     * @return List 返回列表
     */
    <T> List<T> queryList(@Param("model") T model, @Param("pager") Pager pager);
    
    /**
     * 根据主键ID查找对象
     * 
     * @param id 主键ID
     * @return 对象
     */
    <T> T getById(Object id);
}
