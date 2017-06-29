package com.oracle.jsp.bean;

public class PagingBean {
	
	private int totalCount;//������
	
	private int pageCount;//��ҳ��
	
	private int currentPage;//��ǰҳ
	
	private int countPerPage;//һҳ����������
	
	private String prefixUrl;
	
	
	private boolean isAnd;//true��ʱ����&�����ǣ�
	//���õ�ǰҳ������������ÿҳ����������
	public PagingBean(int currentPage,int totalCount,int countPerPage)
	{
		this.countPerPage = countPerPage;
		pageCount = (totalCount - 1)/countPerPage + 1;
		
		//����ÿҳ����������С�������������ڵ���0
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
