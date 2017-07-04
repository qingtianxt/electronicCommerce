package com.oracle.jsp.bean;

import java.util.List;

/**
 * ����Bean.
 * 
 * @author Administrator
 *
 */
public class OrderBean {
	private int id;
	private String code;//�������
	private float original_price;//����ԭ�ۣ�������Ʒԭ����ӵļ۸�
	private float price;//��Ʒ�۸񣬣����У�
	private int address_id;//�ջ���ַ
	private AddressBean addressBean;
	private int user_id;//�û�id
	private UserBean userBean;
	private int payment_type;//֧�����ͣ�0 ����֧����1���������
	private int status;//����״̬(δ֧������֧����δ�ջ������ջ�)
	private String create_date;//����ʱ��
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