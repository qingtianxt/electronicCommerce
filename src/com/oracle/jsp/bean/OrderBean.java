package com.oracle.jsp.bean;

import java.util.List;

/**
 * 订单Bean.
 * 
 * @author Administrator
 *
 */
public class OrderBean {
	private int id;
	private String code;//订单编号
	private float original_price;//订单原价（所有商品原价相加的价格）
	private float price;//商品价格，（所有）
	private int address_id;//收货地址
	private AddressBean addressBean;
	private int user_id;//用户id
	private UserBean userBean;
	private int payment_type;//支付类型（0 在线支付，1，货到付款）
	private int status;//订单状态(未支付，已支付，未收货，已收货)
	private String create_date;//创建时间
	private List<OrderProductBean> orderProductBeans;

	public List<OrderProductBean> getOrderProductBeans() {
		return orderProductBeans;
	}

	public void setOrderProductBeans(List<OrderProductBean> orderProductBeans) {
		this.orderProductBeans = orderProductBeans;
	}

	public OrderBean() {
	}

	public OrderBean(String code, UserBean userBean) {
		this.setCode(code);
		this.setUserBean(userBean);
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(float original_price) {
		this.original_price = original_price;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public AddressBean getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public int getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(int payment_type) {
		this.payment_type = payment_type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
}