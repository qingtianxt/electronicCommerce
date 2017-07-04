package com.oracle.jsp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oracle.jsp.bean.AddressBean;
import com.oracle.jsp.util.DBUtil;

public class AddressDao {

	/**
	 * 通过用户id获取地址信息
	 * @param id
	 * @return
	 */
	public List<AddressBean> getAddressList(int id) {
		
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		String sql = "select id,name,province,city,region,address,cellphone,user_id,status,create_date from user_address where user_id='"+id+"'";
		
		List<AddressBean> list = new ArrayList<>();
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while(rs.next()){
				AddressBean addressBean = new AddressBean();
				addressBean.setId(rs.getInt("id"));
				addressBean.setName(rs.getString("name"));
				addressBean.setProvince(rs.getInt("province"));
				addressBean.setCity(rs.getInt("city"));
				addressBean.setRegion(rs.getInt("region"));
				addressBean.setAddress(rs.getString("address"));
				addressBean.setCellphone(rs.getString("cellphone"));
				addressBean.setUser_id(rs.getInt("user_id"));
				addressBean.setStatus(rs.getInt("status"));
				addressBean.setCreate_date(rs.getString("create_date"));
				addressBean.setProvincename(new AddressDao().getProvinceById(addressBean.getProvince()));
				addressBean.setCityname(new AddressDao().getCityById(addressBean.getCity()));
				addressBean.setAreaname(new AddressDao().getAreabyId(addressBean.getRegion()));
//				System.out.println(addressBean.getRegion());
				list.add(addressBean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(rs, state, conn);
		return list;
	}
	private String getAreabyId(int region) {
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs =null;
		
		String area="";
		
		String sql  = "select name from area where id='"+region+"'";
		
		try {
			state  = conn.createStatement();
			
			rs = state.executeQuery(sql);
			
			while(rs.next()){
				area = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return area;
	}
	/**
	 * 通过城市id获取城市名字
	 * @param city
	 * @return
	 */
	private String getCityById(int city) {
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs =null;
		
		String city1="";
		
		String sql  = "select name from city where id='"+city+"'";
		
		try {
			state  = conn.createStatement();
			
			rs = state.executeQuery(sql);
			
			while(rs.next()){
				city1 = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return city1;
	}
	/**
	 * 通过省份id获取省份姓名
	 * @param province
	 * @return
	 */
	private String getProvinceById(int province) {
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs =null;
		
		String province1="";
		
		String sql  = "select name from province where id='"+province+"'";
		
		try {
			state  = conn.createStatement();
			
			rs = state.executeQuery(sql);
			
			while(rs.next()){
				province1 = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return province1;
			
		}
/**
 * 通过id获取地址信息
 * @param addressId
 * @return
 */
	public AddressBean getAddressById(int addressId) {
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs =null;
		
		AddressBean addressBean  = new AddressBean();
		
		String sql  = "select id,name,province,city,region,address,cellphone,user_id,status,create_date from user_address where id='"+addressId+"'";
		
		try {
			state  = conn.createStatement();
			
			rs = state.executeQuery(sql);
			
			while(rs.next()){
				addressBean.setId(rs.getInt("id"));
				addressBean.setName(rs.getString("name"));
				addressBean.setProvince(rs.getInt("province"));
				addressBean.setCity(rs.getInt("city"));
				addressBean.setRegion(rs.getInt("region"));
				addressBean.setAddress(rs.getString("address"));
				addressBean.setCellphone(rs.getString("cellphone"));
				addressBean.setUser_id(rs.getInt("user_id"));
				addressBean.setStatus(rs.getInt("status"));
				addressBean.setCreate_date(rs.getString("create_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBean;
	}

}
