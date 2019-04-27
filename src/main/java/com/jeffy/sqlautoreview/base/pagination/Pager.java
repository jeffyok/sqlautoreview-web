package com.jeffy.sqlautoreview.base.pagination;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
/**
 * 分页对象  
 * @ClassName: Pager    
 * @author 陈剑飞    
 * @date 2016年3月29日 上午11:24:50    
 * @version  v 1.0
 * @param <T>
 */
public class Pager<T> {
	/**
	 * 结果列表
	 */
	private List<T> list;
	/**
	 * 页码
	 */
	private int pageNo=1;
	/**
	 * 每页的大小
	 */
	private int pageSize=20;
	/**
	 * 总行数
	 */
	private int totalCount;
	
	/**
	 * 排序字段
	 */
	private String sort;

	/**
	 * 排序顺序
	 */
	private String order = ASC;
	
	/**
	 * 升序
	 */
	public final static String ASC ="ASC";
	
	/**
	 * 降序
	 */
	public final static String DESC ="DESC";
	
	public Pager(){
		
	}
	
	public Pager(int pageNo,int pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public Pager(int pageNo,int pageSize,String sort,String order){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sort = sort;
		this.order = order;
	}
	
	public List<T> getList() {
		return list;
	}
	public int getPageNo() {
		return pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getBeginIndex()
	{
	    int index = (getPageNo()-1) * getPageSize();
		return index <=0 ? 0 :index;
	}
	
	public String getOrderCondition(){
		if(StringUtils.isNotEmpty(getSort())){
		    String sortTemp = getSort();
		    if(!sortTemp.contains("_")){
		        sortTemp = camelToUnderLineName(sortTemp);
		    }
			return "order by "+sortTemp+" "+(getOrder()!=null?getOrder():"");
		}else{
			return "";
		}
	}
	
	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String camelToUnderLineName(String camelName) {
	    StringBuilder result = new StringBuilder();
	    if (camelName != null && camelName.length() > 0) {
	        // 将第一个字符处理成大写
	        result.append(camelName.substring(0, 1).toUpperCase());
	        // 循环处理其余字符
	        for (int i = 1; i < camelName.length(); i++) {
	            String s = camelName.substring(i, i + 1);
	            // 在大写字母前添加下划线
	            if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0)) && !".".equals(s)) {
	                result.append("_");
	            }
	            // 其他字符直接转成大写
	            result.append(s.toUpperCase());
	        }
	    }
	    return result.toString();
	}
	
	
}
