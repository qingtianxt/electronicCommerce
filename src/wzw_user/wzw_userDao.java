package wzw_user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class wzw_userDao {
	/**
	 * 登录
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
				// 如果有结果，是认为是通过验证了
				if (rs.getString("password").equals(MD5.GetMD5Code(password + rs.getString("salt")))) {
					// 跟注册和修改的时候加密过程是一样的
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
 * 更新个人信息
 * @param id
 * @param phone
 * @param nickname
 * @param password
 * @return
 */
	public boolean update(String id, String phone, String nickname, String password) {
		Connection conn =  DBUtil.getConn();
		Statement state = null;
		boolean flag= true;
		int i=0;
		String sql = "update user set username='"+phone+"',password='"+password+"',nickname='"+nickname+"' where id='"+id+"'";
		
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
	public boolean updatepic(String path,String id) {
		Connection conn =  DBUtil.getConn();
		Statement state = null;
		boolean flag= true;
		int i=0;
		String sql = "update user set pic='"+path+"' where id='"+id+"'";
		
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
	
}
