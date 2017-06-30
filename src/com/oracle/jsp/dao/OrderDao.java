package com.oracle.jsp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oracle.jsp.bean.AddressBean;
import com.oracle.jsp.bean.UserBean;
import com.oracle.jsp.bean.UserOrderBean;
import com.oracle.jsp.util.DBUtil;

public class OrderDao {

	public List<UserOrderBean> search(String username) {
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs= null;
		List<UserOrderBean> list = new ArrayList<>();
		String sql = "select * from user join user_order on user.id = user_order.user_id join user_address on user_order.user_id=user_address.user_id where username='"+username+"'";
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			
			while(rs.next()){
					UserOrderBean userOrderBean = new UserOrderBean();
					userOrderBean.setId(rs.getInt("user_order.id"));
					userOrderBean.setCode(rs.getString("user_order.code"));
					userOrderBean.setOriginal_price(rs.getFloat("user_order.original_price"));
					userOrderBean.setPrice(rs.getFloat("user_order.price"));
					userOrderBean.setAddress_id(rs.getInt("user_order.address_id"));
					userOrderBean.setUser_id(rs.getInt("user_order.user_id"));
					userOrderBean.setPayment_type(rs.getInt("user_order.payment_type"));
					userOrderBean.setStatus(rs.getInt("user_order.status"));
					userOrderBean.setCreate_date(rs.getString("user_order.create_date"));
					UserBean userBean =new UserBean(rs.getString("user.username"),rs.getString("user.password"),rs.getString("user.salt"),rs.getString("user.nickname"),rs.getString("user.truename"),rs.getInt("user.sex"),rs.getString("user.pic"),rs.getInt("user.status"),rs.getString("user.create_date"));
					AddressBean addressBean = new AddressBean(rs.getInt("user_address.id"),rs.getString("user_address.name"),rs.getInt("user_address.province"),rs.getInt("user_address.city"),rs.getInt("user_address.region"),rs.getString("user_address.address"),rs.getString("user_address.cellphone"),rs.getInt("user_address.user_id"),rs.getInt("user_address.status"),rs.getString("user_address.create_date"));
					userOrderBean.setUserBean(userBean);
					userOrderBean.setAddressBean(addressBean);
					list.add(userOrderBean);
//					System.out.println(userOrderBean.toString());
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public boolean deletebyId(String orderId) {
		Connection conn = DBUtil.getConn();
		Statement state =null;
		boolean flag=true;
		int i=0;
		String sql ="delete from user_order where id='"+orderId+"'";
		
		try {
			state = conn.createStatement();
			i= state.executeUpdate(sql);
			
			if(i==0){
				flag=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return flag;
	}

}
