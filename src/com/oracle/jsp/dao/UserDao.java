package com.oracle.jsp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oracle.jsp.bean.AdminBean;
import com.oracle.jsp.bean.UserBean;
import com.oracle.jsp.util.DBUtil;
import com.oracle.jsp.util.MD5;

public class UserDao {
	/**
	 * 添加用户信息
	 * @param userBean
	 * @return
	 */
	public boolean add(UserBean userBean) {
		boolean flag=true;
		Connection conn = DBUtil.getConn();
		String sql = "insert into user(username,password,salt,nickname,truename,sex,pic,status,create_date) values ('"+userBean.getUsername()+"','"+userBean.getPassword()+"','"+userBean.getSalt()+"','"+userBean.getNickname()+"','"+userBean.getTruename()+"','"+userBean.getSex()+"','"+userBean.getPic()+"','"+userBean.getStatus()+"',now())";
		Statement state =null;
//		System.out.println(sql);
		try {
			state= conn.createStatement();
			int i = state.executeUpdate(sql);
			if(i==0){
				flag=false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(state, conn);
		return flag;
	}
/**
 * 
 * @param username
 * @return
 */
	public boolean checkUsername(String username) {
		boolean flag=true;
		Connection conn =DBUtil.getConn();
		String sql ="select username from user";
		Statement state = null;
		ResultSet rs =null;
		try {
			 state=conn.createStatement();
			 rs = state.executeQuery(sql);
			
			while(rs.next()){
				if (username.equals(rs.getString("username"))) {

					flag = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(rs, state, conn);
		return flag;
	}
	
	/**
	 * 展示所有用户的信息
	 * @return
	 */
	public List<UserBean> list() {
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		
		String sql = "select * from user";
		List<UserBean> list = new ArrayList<>();
		try {
			state = conn.createStatement();
			
			rs = state.executeQuery(sql);
				while(rs.next()){
					UserBean userBean = new UserBean();
					userBean.setId(rs.getInt("id"));
					userBean.setUsername(rs.getString("username"));
					userBean.setPassword(rs.getString("password"));
					userBean.setSalt(rs.getString("salt"));
					userBean.setNickname(rs.getString("nickname"));
					userBean.setTruename(rs.getString("truename"));
					userBean.setSex(rs.getInt("sex"));
					userBean.setPic(rs.getString("pic"));
					userBean.setStatus(rs.getInt("status"));
					userBean.setCreateDate(rs.getString("create_date"));
					list.add(userBean);
				}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(rs, state, conn);
		return list;
	}
	/**
	 * 根据用户的用户名来查找这个人的详细信息
	 * @param username
	 * @return
	 */
	public UserBean search(String username) {
		UserBean userBean = new UserBean();
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;

		String sql = "select * from user";
//		System.out.println(sql);
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			
			while(rs.next()){
				if(rs.getString("username").equals(username))
				{
					userBean.setId(rs.getInt("id"));
					userBean.setUsername(rs.getString("username"));
					userBean.setPassword(rs.getString("password"));
					userBean.setSalt(rs.getString("salt"));
					userBean.setNickname(rs.getString("nickname"));
					userBean.setTruename(rs.getString("truename"));
					userBean.setSex(rs.getInt("sex"));
					userBean.setPic(rs.getString("pic"));
					userBean.setStatus(rs.getInt("status"));
					userBean.setCreateDate(rs.getString("create_date"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userBean;
	}
	public boolean updateStatus(int status,int id) {
		Connection conn=  DBUtil.getConn();
		Statement state =null;
		boolean flag=true;
		String sql = "update user set status = "+status+" where id="+id;
//		System.out.println(sql);
		int i=0;
		try {
			state = conn.createStatement();
			 i = state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(state, conn);
		if(i==0){
			flag=false;
		}
		return flag;
	}
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public UserBean login(String username, String password) {
		Connection conn = DBUtil.getConn();
		UserBean userBean = null;
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select id,username,password,salt,nickname,truename,sex,pic,status,create_date from user where username='" + username + "'");
			if (rs.next()) {
				// 如果有结果，是认为是通过验证了
				if (rs.getString("password").equals(MD5.GetMD5Code(password + rs.getString("salt")))) {
					// 跟注册和修改的时候加密过程是一样的
					userBean = new UserBean();
					userBean.setId(rs.getInt("id"));
					userBean.setUsername(rs.getString("username"));
					userBean.setPassword(rs.getString("password"));
					userBean.setSalt(rs.getString("salt"));
					userBean.setNickname(rs.getString("nickname"));
					userBean.setTruename(rs.getString("truename"));
					userBean.setSex(rs.getInt("sex"));
					userBean.setPic(rs.getString("pic"));
					userBean.setStatus(rs.getInt("status"));
					userBean.setCreateDate(rs.getString("create_date"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBUtil.close(rs, state, conn);
		return userBean;
	}

}
