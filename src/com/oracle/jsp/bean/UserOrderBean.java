package com.oracle.jsp.bean;

public class UserOrderBean {
	private int id;
	private String code;//订单编号
	private float original_price;//原价
	private float price;//现价
	private int address_id;//联系方式
	private int user_id;//用户id
	private int payment_type;//支付方式
	private int status;//订单状态
	private String create_date;//创建日期
	private UserBean userBean;//
	private AddressBean addressBean;
	
	

	@Override
	public String toString() {
		return "UserOrderBean [id=" + id + ", code=" + code + ", original_price=" + original_price + ", price=" + price
				+ ", address_id=" + address_id + ", user_id=" + user_id + ", payment_type=" + payment_type + ", status="
				+ status + ", date=" + create_date + ", userBean=" + userBean + ", addressBean=" + addressBean + "]";
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public AddressBean getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}

	public UserOrderBean(){
		
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
