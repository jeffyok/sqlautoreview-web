/**
 * (C) 2011-2012 Alibaba Group Holding Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 * 
 *
 * Authors:
 *   danchen <danchen@taobao.com>
 *
 */
package com.jeffy.sqlautoreview.autoreview;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jeffy.sqlautoreview.mapper.ReviewResultMapper;
import com.jeffy.sqlautoreview.model.ProjectModel;
import com.jeffy.sqlautoreview.model.ReviewResultModel;
import com.jeffy.sqlautoreview.model.SqlreviewModel;
/*
 * 对每个table的索引进行合并
 * author:danchen / zhaolin
 * create_time:2012/1/30
 */
public class MergeIndex {
	 //log4j日志
    private static Logger logger = Logger.getLogger(MergeIndex.class);
    //所有的表名,从sqlreviewdb中获得
    private Set<String> set_tablenames;
    //所有真实的表名,分库分表的问题,实际的表名与上面的表名会有一些差异
    private Set<String> set_real_tablenames;
    //从sqlreviewdb中获得的新创建的索引
    private HashMap<String, String> map_new_tablename_indexes;
    //从目标数据库中获得已经存在的索引
    private HashMap<String, String> map_tablename_indexes;
    private HashMap<String, String> tmp_map_tablename_indexes;
    //最后合并索引后的结果
    private HashMap<String, String> map_result;
    //操作目标数据库,获取相应元数据
    private IMetaData iMetaData;
    private ProjectModel projectModel;
    /*
     * 构造函数
     */
    public  MergeIndex(ProjectModel projectModel) 
    {
		set_tablenames=new HashSet<String>();
		set_real_tablenames=new HashSet<String>();
		map_tablename_indexes=new HashMap<String, String>();
		tmp_map_tablename_indexes=new HashMap<String, String>();
		map_new_tablename_indexes=new HashMap<String, String>();
		map_result=new HashMap<String, String>();
		iMetaData=new MySQLMetaData(projectModel);
		this.projectModel = projectModel;
	}

    /*
     * 获得审核出的索引
     */
    private void getNewIndexes(List<SqlreviewModel> auditSqlReviewList)
    {
    	String table_name;
    	for(SqlreviewModel sqlreview:auditSqlReviewList)
    	{
    		//无表名的时候退出
    		if(sqlreview.getTableName()==null){
    			continue;
    		}
    		//多表以,分隔
    		String[] array_tablenames=sqlreview.getTableName().split(",");
    		for(int k=0;k<array_tablenames.length;k++)
    		{
    			table_name=array_tablenames[k];
    			set_tablenames.add(table_name);
    		}
    		
    		if(sqlreview.getSqlAutoIndex()!=null){//jeffy修改 空指针判断
    			String[] array_indexes=sqlreview.getSqlAutoIndex().split(";");
        		for(int i=0;i<array_indexes.length;i++)
        		{
        			//主键索引直接排除,这个已重用,不需要合并
        			if(array_indexes[i].indexOf("PRIMARY")>=0){
        				continue;
        			}
        			//唯一键索引直接排除,这个已重用,不需要合并
        			if(array_indexes[i].indexOf("UNIQUE")>=0){
        				continue;
        			}
        			//其它新建索引,需要加入到list里面
        			int addr=array_indexes[i].indexOf("create index");
        			String indexString;
        			if(addr>=0){
        				table_name=getTableNameByCreateIndexScript(array_indexes[i].substring(addr));
        				indexString=map_new_tablename_indexes.get(table_name);
        				if(indexString != null){
        					indexString=indexString+";"+array_indexes[i].substring(addr);
        					map_new_tablename_indexes.put(table_name, indexString);
        				}else {
    						map_new_tablename_indexes.put(table_name, array_indexes[i].substring(addr));
    					}
        			}else{
        				//
        				logger.warn("检测到非法脚本");
        			}
        		}
    		}
    		
    	}
    }
    
    /*
     * 从创建索引的脚本里分析中表名
     */
    private String getTableNameByCreateIndexScript(String createindexscript) 
    {
		int addr_on=createindexscript.indexOf(" on ");
		int addr_left_kuohao=createindexscript.indexOf("(");
		String table_name=createindexscript.substring(addr_on+4, addr_left_kuohao).trim();
		return table_name;
	}
    
    /*
     * 获得所有已存在的索引
     */
    private void getExistIndexes()
    {
    	if(set_tablenames.size()==0){
    		logger.warn("getExistIndexes:不存在任何的表");
    		return;
    	}
    	
    	String all_indexes=iMetaData.getIndexesByTableName2(getAllTables());
    	fill_map_tablename_indexes(all_indexes);
    }
    
    /*
     * 返回去重后的表名字符串
     */
    private String getAllTables()
    {
    	String tablenames="";
    	for(Iterator<String> iterator=set_tablenames.iterator();iterator.hasNext();)
    	{
    		tablenames=tablenames+","+iterator.next();
    	}
    	tablenames=tablenames.substring(1);
    	return tablenames;
    }
    /*
     * 分拣索引,将不同表的索引都放入到map_tablename_indexes hash map里
     */
    private void fill_map_tablename_indexes(String all_indexes) 
    {
    	int addr_maohao;
    	String table_name;
    	String indexes;
		String[] array_table_indexes=all_indexes.split("\\|");
		logger.debug(array_table_indexes.length);
		logger.debug(getAllTables());
		for(int i=0;i<array_table_indexes.length;i++)
		{
			addr_maohao=array_table_indexes[i].indexOf(":");
			//这个table_name有可能是分表名
			table_name=array_table_indexes[i].substring(0, addr_maohao);
			indexes=array_table_indexes[i].substring(addr_maohao+1);
			tmp_map_tablename_indexes.put(table_name, indexes);
			//将indexes变成创建索引的脚本
			indexes=transferToCreateIndexScript(table_name,indexes);
			map_tablename_indexes.put(table_name, indexes);
		}
	}
	
    /*
     * 将indexes变成创建索引的脚本
     * indexes格式PRIMARY(seller_id);idx_seller_nick(nick);
     */
	private String transferToCreateIndexScript(String table_name,String indexes) 
	{
		int addr_left_kuohao;
		String index_name;
		String index_columns;
		String createindexscript="";
		String[] array_index=indexes.split(";");
		for(int i=0;i<array_index.length;i++)
		{
			//这里展示原表的索引,primary index也需要展示一下
			/*
			if(array_index[i].indexOf("PRIMARY")>=0){
				continue;
			}
			*/
			addr_left_kuohao=array_index[i].indexOf("(");
			index_name=array_index[i].substring(0, addr_left_kuohao);
			index_columns=array_index[i].substring(addr_left_kuohao);
			if(createindexscript.equals("")){
				createindexscript="create index "+index_name+" on "+table_name+index_columns+";";
			}else {
				createindexscript=createindexscript+"create index "+index_name+" on "+table_name+index_columns+";";
			}
			
		}
		
		return createindexscript;
	}
	
	/*
	 * 将一个表的所有建索引的脚本转换成一个List<MergeIndex_Node>
	 */
	private List<MergeIndex_Node> transferToListMergeIndexNode(
			String indexscripts) 
	{
		if(indexscripts==null || indexscripts.length()==0){
			logger.warn("transferToListMergeIndexNode:indexscripts==null || indexscripts.length()==0");
			return null;
		}
		List<MergeIndex_Node> list=new LinkedList<MergeIndex_Node>();
		String[] array_index_script=indexscripts.split(";");
		for(int i=0;i<array_index_script.length;i++)
		{
			MergeIndex_Node mergeIndex_Node=new MergeIndex_Node(array_index_script[i]);
			list.add(mergeIndex_Node);
		}
		
		return list;
	}
	
	/*
	 * 外部调用的方法
	 * 将所有表的索引进行合并
	 */
	public void mergeAllTableIndexes(List<SqlreviewModel> auditSqlReviewList,ReviewResultMapper reviewResultMapper) 
	{
		String table_name;
		String real_table_name;
		String exist_indexes;
		String new_indexes;
		String last_indexes;
	    List<MergeIndex_Node> list_table_new_indexes;
	    List<MergeIndex_Node> list_table_exist_index;
	    //保存返回结果
	    List<String> list;
	    
	    
	    //填冲下面三个数据结构
	    //set_tablenames;
	    //map_new_tablename_indexes;
	    //map_tablename_indexes;
	    //下面两个函数的顺序不能够调换
	    getNewIndexes(auditSqlReviewList);
	    getExistIndexes();
	    
	    
		for(Iterator<String> iterator=set_tablenames.iterator();iterator.hasNext();)
		{
			table_name=iterator.next();
			//分库分表,表名需要处理
			real_table_name=iMetaData.findMatchTable(table_name);
			if(real_table_name==null){
				logger.warn("mergeAllTableIndexes: Table "+table_name+" doesn't exist.");
				continue;
			}
			if(!real_table_name.equals(table_name)){
				//发生了表名替换
				table_name=real_table_name;
			}
			//保存处理的真实表名
			set_real_tablenames.add(table_name);
			exist_indexes=map_tablename_indexes.get(table_name);
			new_indexes=map_new_tablename_indexes.get(table_name);
			list_table_exist_index=transferToListMergeIndexNode(exist_indexes);
			list_table_new_indexes=transferToListMergeIndexNode(new_indexes);
			TableMergeIndex tmi=new TableMergeIndex(table_name);
			list=tmi.tableMergeIndexService(list_table_exist_index, list_table_new_indexes);
			last_indexes=getLastIndexesFromList(list);
			map_result.put(table_name, last_indexes);
		}
		saveToSqlReviewDb(reviewResultMapper);
		print_merge_result();
	}

	/*
	 * 将merge结果保存到数据库中
	 */
	private void saveToSqlReviewDb(ReviewResultMapper reviewResultMapper) 
	{
		Date now = new Date();
		String tablename;
		String real_tablename;
		String exist_indexes;
		String new_indexes;
		String merge_result;
		List<ReviewResultModel> reviewResultModelList = new ArrayList<ReviewResultModel>();
		ReviewResultModel reviewResultModel = null;
		for(Iterator<String> iterator=set_real_tablenames.iterator();iterator.hasNext();)
		{
			tablename="";
			real_tablename=iterator.next();
			exist_indexes=tmp_map_tablename_indexes.get(real_tablename);
			new_indexes=map_new_tablename_indexes.get(real_tablename);
			merge_result=map_result.get(real_tablename);
			if(!set_tablenames.contains(real_tablename)){
				for(Iterator<String> iterator2=set_tablenames.iterator();iterator2.hasNext();)
				{
					String tmp_tablename=iterator2.next();
					if(real_tablename.indexOf(tmp_tablename)==0){
						tablename=tmp_tablename;
						break;
					}
				}
			}else {
				tablename=real_tablename;
			}
			reviewResultModel = new ReviewResultModel();
			reviewResultModel.setProjectId(projectModel.getProjectId());
			reviewResultModel.setTablename(tablename);
			reviewResultModel.setRealTablename(real_tablename);
			reviewResultModel.setExistIndexes(exist_indexes);
			reviewResultModel.setNewIndexes(new_indexes);
			reviewResultModel.setMergeResult(merge_result);
			reviewResultModel.setCreateTime(now);
			reviewResultModel.setUpdateTime(now);
			reviewResultModelList.add(reviewResultModel);
		}
		if(reviewResultModelList!= null && reviewResultModelList.size() > 0){
			reviewResultMapper.addBatch(reviewResultModelList);
		}
	}

	/*
	 * 示例输出最后的merge结果
	 */
	private void print_merge_result() 
	{
		String tablename;
		String exist_indexes;
		String new_indexes;
		String result_indexes;
		for(Iterator<String> iterator=set_real_tablenames.iterator();iterator.hasNext();)
		{
			tablename=iterator.next();
			exist_indexes=tmp_map_tablename_indexes.get(tablename);
			new_indexes=map_new_tablename_indexes.get(tablename);
			result_indexes=map_result.get(tablename);
			logger.info("---------------------------------------------------");
			logger.info("Table "+tablename+" Merge index information as follows:");
			logger.info("---------------------------------------------------");
			if(!set_tablenames.contains(tablename)){
				logger.warn("分库分表提示:"+tablename+" 是子表.");
			}
			logger.info("Exist indexes as follows:");
			print_index(exist_indexes);
			logger.info("New indexes as follows:");
			print_index(new_indexes);
			logger.info("Result indexes as follows:");
			print_index(result_indexes);
		}
		
	}

	private void print_index(String str_indexes)
	{
		if(str_indexes==null) return;
		String[] array_indexes=str_indexes.split(";");
        for(int i=0;i<array_indexes.length;i++)
        {
      	  logger.info(array_indexes[i]);
        }
	}
	
	/*
	 * 将List中的index拼接成字符串的方式
	 */
	private String getLastIndexesFromList(List<String> list) 
	{
		String last_indexes="";
		if(list==null ||list.size()==0){
			return last_indexes;
		}
		
		for(Iterator<String> iterator=list.iterator();iterator.hasNext();)
		{
			last_indexes=last_indexes+";"+iterator.next();
		}
		last_indexes=last_indexes.substring(1);
		return last_indexes;
	}
}
