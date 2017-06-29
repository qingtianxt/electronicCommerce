package com.oracle.jsp.bean;

public class PagingBean {
	
	private int totalCount;//总数量
	
	private int pageCount;//总页数
	
	private int currentPage;//当前页
	
	private int countPerPage;//一页多少条数据
	
	private String prefixUrl;
	
	
	private boolean isAnd;//true的时候是&否则是？
	//设置当前页，总数量，和每页多少条数据
	public PagingBean(int currentPage,int totalCount,int countPerPage)
	{
		this.countPerPage = countPerPage;
		pageCount = (totalCount - 1)/countPerPage + 1;
		
		//控制每页的数据量，小于总数量，大于等于0
		if(currentPage>=pageCount){
			currentPage = pageCount - 1;
		}
		if(currentPage < 0){
			currentPage = 0;
		}
		
		this.totalCount = totalCount;
		this.currentPage = currentPage;
	}
	public PagingBean(){
		
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	public String getPrefixUrl() {
		return prefixUrl;
	}
	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}
	public boolean isAnd() {
		return isAnd;
	}
	public void setAnd(boolean isAnd) {
		this.isAnd = isAnd;
	}
	@Override
	public String toString() {
		return "PagingBean [totalCount=" + totalCount + ", pageCount=" + pageCount + ", currentPage=" + currentPage
				+ ", countPerPage=" + countPerPage + ", prefixUrl=" + prefixUrl + ", isAnd=" + isAnd + "]";
	}
	
}
