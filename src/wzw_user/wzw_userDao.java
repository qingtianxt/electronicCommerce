package wzw_user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class wzw_userDao {
	/**
	 * ��¼
	 * @param username
	 * @param password
	 * @return
	 */
	public userBean login(String username, String password) {
		Connection conn = DBUtil.getConn();
		userBean userBean = null;
		Statement state = null;
		ResultSet rs = null;
		try {
			state = conn.createStatement();
			rs = state.executeQuery("select id,username,password,salt,nickname,truename,sex,pic,status,create_date from user where username='" + username + "'");
			if (rs.next()) {
				// ����н��������Ϊ��ͨ����֤��
				if (rs.getString("password").equals(MD5.GetMD5Code(password + rs.getString("salt")))) {
					// ��ע����޸ĵ�ʱ����ܹ�����һ����
					userBean = new userBean();
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
/**
 * ���¸�����Ϣ
 * @param id
 * @param phone
 * @param nickname
 * @param password
 * @return
 */
	public boolean update(userBean userbean) {
		Connection conn =  DBUtil.getConn();
		Statement state = null;
		boolean flag= true;
		int i=0;
		String sql = "update user set username='"+userbean.getUsername()+"',password='"+userbean.getPassword()+"',nickname='"+userbean.getNickname()+"' where id='"+userbean.getId()+"'";
		
		try {
			state = conn.createStatement();
			i=state.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(state, conn);
		}
		if(i==0){
			flag=false;
		}
		return flag;
	}
	/**
	 * ����ͷ��
	 * @param path
	 * @param id
	 * @return
	 */
	public boolean updatepic(String path,userBean userbean) {
		Connection conn =  DBUtil.getConn();
		Statement state = null;
		boolean flag= true;
		int i=0;
		String sql = "update user set pic='"+path+"' where id='"+userbean.getId()+"'";
		
		try {
			state = conn.createStatement();
			i=state.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(state, conn);
		}
		if(i==0){
			flag=false;
		}
		return flag;
	}
	/**
	 * ͨ��id��ȡ
	 * @param id
	 * @return
	 */
	public userBean getUserbyId(String id) {
		
		Connection conn = DBUtil.getConn();
		userBean userBean = null;
		Statement state = null;
		ResultSet rs = null;
		userBean = new userBean();
		try {
			state = conn.createStatement();
			
			rs = state.executeQuery("select id,username,password,salt,nickname,truename,sex,pic,status,create_date from user where id='" + id + "'");
			while(rs.next()) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBUtil.close(rs, state, conn);
		return userBean;
	}
	
}
