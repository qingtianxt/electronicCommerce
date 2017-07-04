package com.oracle.jsp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.oracle.jsp.bean.OrderBean;
import com.oracle.jsp.util.DBUtil;

public class OrderDao {
	/**
	 * 查看订单详情
	 * 
	 * @param id
	 * @return
	 */
	public OrderBean lookById(int id) {
		ResultSet rs = null;
		Statement state = null;
		Connection conn = null;
		OrderBean orderBean = new OrderBean();
		try {
			conn = DBUtil.getConn();
			state = conn.createStatement();
			rs = state.executeQuery("select * from user_order where id='" + id + "'");
			while (rs.next()) {
				OrderProductDao orderProductDao = new OrderProductDao();
				orderBean.setId(rs.getInt("id"));
				orderBean.setCode(rs.getString("code"));
				orderBean.setOriginal_price(rs.getFloat("original_price"));
				orderBean.setPrice(rs.getFloat("price"));
				int addressId = rs.getInt("address_id");
				orderBean.setAddressBean((new AddressDao()).getAddressById(addressId));
				int userId = rs.getInt("user_id");
				orderBean.setUserBean((new UserDao()).search(""+userId));
				orderBean.setPayment_type(rs.getInt("payment_type"));
				orderBean.setStatus(rs.getInt("status"));
				orderBean.setCreate_date(rs.getString("create_date"));
				orderBean.setOrderProductBeans(orderProductDao.getOrderProductBeans(id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return orderBean;
	}

	/**
	 * 获取订单列表
	 * 
	 * @return
	 */
	public List<OrderBean> getList(int id) {
		ResultSet rs = null;
		Statement state = null;
		Connection conn = null;
		List<OrderBean> orderBeans = new ArrayList<OrderBean>();
		try {
			conn = DBUtil.getConn();
			state = conn.createStatement();
			rs = state.executeQuery("select * from user_order where user_id='" + id + "'");
			OrderBean bean;
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getInt("id"));
				bean.setCode(rs.getString("code"));
				bean.setPrice(rs.getFloat("price"));
				bean.setCreate_date(rs.getString("create_date"));
				orderBeans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return orderBeans;
	}

	/**
	 * 删除订单
	 * 
	 * @param orderId
	 * @return
	 */
	public boolean deleteById(String orderId) {
		String sql = "delete from user_order where id='" + orderId + "'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		int a = 0;
		boolean f = false;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		if (a > 0) {
			f = true;
		}
		return f;
	}

	/**
	 * 添加订单（信息包括订单编号，原价，现价，用户id 订单创建时间）
	 * 
	 * @param orderBean
	 */
	public boolean addOrder(OrderBean orderBean) {
		String sql = "insert into user_order(code,original_price,price,user_id,create_date) value('"
				+ orderBean.getCode() + "','" + orderBean.getOriginal_price() + "','" + orderBean.getPrice() + "','"
				+ orderBean.getUserBean().getId() + "',now())";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		int a = 0;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		if (a > 0) {
			f = true;
		}
		return f;
	}

	/**
	 * 通过订单编号获取订单
	 * 
	 * @param code
	 * @return
	 */
	public OrderBean getOrderByCode(String code) {
		ResultSet rs = null;
		Statement state = null;
		Connection conn = null;
		OrderBean orderBean = new OrderBean();
		try {
			conn = DBUtil.getConn();
			state = conn.createStatement();
			rs = state.executeQuery("select * from user_order where code='" + code + "'");
			if (rs.next()) {
				orderBean.setId(rs.getInt("id"));
				orderBean.setCode(rs.getString("code"));
				orderBean.setOriginal_price(rs.getFloat("original_price"));
				orderBean.setPrice(rs.getFloat("price"));
				orderBean.setAddress_id(rs.getInt("address_id"));
				orderBean.setUser_id(rs.getInt("user_id"));
				orderBean.setPayment_type(rs.getInt("payment_type"));
				orderBean.setStatus(rs.getInt("status"));
				orderBean.setCreate_date(rs.getString("create_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, state, conn);
		}
		return orderBean;
	}

	/**
	 * 更新订单
	 * 
	 * @param orderBean
	 */
	public boolean upOrder(OrderBean orderBean) {
		String sql = "update user_order set original_price='" + orderBean.getOriginal_price() + "',price='"
				+ orderBean.getPrice() + "',address_id='" + orderBean.getAddress_id() + "',payment_type='"
				+ orderBean.getPayment_type() + "' where id='" + orderBean.getId() + "'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		int a = 0;
		boolean f = false;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		if (a > 0) {
			f = true;
		}
		return f;
	}
/**
 * 通过订单编号删除订单
 * @param code
 * @return
 */
	public boolean delbyCode(String code) {
		
		String sql = "delete from user_order where code='" + code + "'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		int a = 0;
		boolean f = false;
		try {
			state = conn.createStatement();
			a = state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(state, conn);
		}
		if (a > 0) {
			f = true;
		}
		return f;
	}
}