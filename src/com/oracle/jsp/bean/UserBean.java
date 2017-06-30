package com.oracle.jsp.bean;

public class UserBean {
	private int id;
	
	private String username;
	private String password;//密码（MD5）
	private String salt;//盐值
	private int status;//用户状态，是否冻结
	private int sex;//性别
	private String nickname;//昵称
	private String truename;//真实姓名
	private String pic;//头像
	private String createDate;//创建日期
	
	public UserBean(){
		
	}
	public UserBean(String username ,String password,String salt,String nickname,String truename,int sex,String pic,int status,String createDate){
		this.setUsername(username);
		this.setPassword(password);
		this.setSalt(salt);
		this.setStatus(status);
		this.setSex(sex);
		this.setNickname(nickname);
		this.setTruename(truename);
		this.setPic(pic);
		this.setCreateDate(createDate);
	}
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ ", status=" + status + ", sex=" + sex + ", nickname=" + nickname + ", truename=" + truename + ", pic="
				+ pic + ", createDate=" + createDate + "]";
	}
	
	
	
}
