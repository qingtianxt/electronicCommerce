package com.oracle.jsp.bean;

public class AdminBean {

	private int id;//��ÿ���û�����id����ȡ�������ݿ�����
	private String username;//�û���
	private String password;//����
	private String salt;//��ֵ
	private String createDate;//�˺Ŵ�������

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
