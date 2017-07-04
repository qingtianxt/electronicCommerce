package com.oracle.jsp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oracle.jsp.bean.OrderProductBean;
import com.oracle.jsp.util.DBUtil;

public class OrderProductDao {
/**
 * 通过订单编号，获取商品订单信息
 * @param id
 * @return
 */
	public List<OrderProductBean> getOrderProductBeans(int id) {
		List<OrderProductBean> list = new ArrayList<>();
		
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		
		String sql  = "select id,order_id,product_id,price,number,create_date from user_order_product where order_id='"+id+"'";
		
		try {
			state = conn.createStatement();
			
			rs = state.executeQuery(sql);
			
			while(rs.next()){
				OrderProductBean orderProductBean = new OrderProductBean();
				
				orderProductBean.setId(rs.getInt("id"));
				orderProductBean.setOrderId(rs.getInt("order_id"));
				orderProductBean.setProductId(rs.getInt("product_id"));
				orderProductBean.setPrice(rs.getInt("price"));
				orderProductBean.setPrice(rs.getInt("number"));
				orderProductBean.setDatetime(rs.getString("create_date"));
				list.add(orderProductBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(rs, state, conn);
		return list;
	}
/**
 * 添加商品订单信息
 * @param productOrderBean
 */
	public void addOrderProduct(OrderProductBean productOrderBean) {
		
		Connection conn = DBUtil.getConn();
		Statement state = null;
		
		String sql = "insert into user_order_product(order_id,product_id,price,number,create_date) values ('"+productOrderBean.getOrderBean().getId()+"','"+productOrderBean.getProductBean().getId()+"','"+productOrderBean.getProductBean().getPrice()+"','"+productOrderBean.getNumber()+"',now())";
		
		try {
			state = conn.createStatement();
			
			state.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(state, conn);
	}

}
