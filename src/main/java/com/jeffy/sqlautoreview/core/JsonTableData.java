package com.jeffy.sqlautoreview.core;

import java.util.List;

/**  
 * 表格json数据  
 * @ClassName: JsonTableData    
 * @author 陈剑飞    
 * @date 2016年8月21日 下午5:39:30    
 * @version  v 1.0
 * @param <T>    
 */
public class JsonTableData<T> {
	/**
	 * 总条数
	 */
	private int total;
	/**
	 * 数据行
	 */
	private List<T> rows;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
