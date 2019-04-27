package com.jeffy.sqlautoreview.base.pagination;

/**
 * easy ui 分页对象
 * @ClassName: EasyUiPager    
 * @author 陈剑飞    
 * @date 2016年3月29日 下午3:36:35    
 * @version  v 1.0
 */
public class EasyUiPager extends Pager{
	/**
	 * 页码
	 */
	private int page = 1;

	/**
	 * 每页条数
	 */
	private int rows = 20;

	public EasyUiPager(){
	    
	}
	
	public EasyUiPager(int page,int rows){
	    this.page = page;
	    this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public int getPageNo() {
        return this.getPage();
    }
	
    public void setPageNo(int pageNo) {
        this.setPageNo(pageNo);
        this.setPage(pageNo);
    }
	
	public int getPageSize() {
        return this.getRows();
    }
	
    public void setPageSize(int pageSize) {
        this.setPageSize(pageSize);
        this.setRows(pageSize);
    }
}
