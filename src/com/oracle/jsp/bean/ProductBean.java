package com.oracle.jsp.bean;

import java.util.List;

public class ProductBean {
	private int id;
	private String name;
	private float originalPrice;
	private float price;
	private String intro;
	private int productTypeId;
	private ProductTypeBean productTypeBean;
	private int number;
	private int sellNumber;
	private String pic;
	private String productProperties;
	private List<ProductOptionBean> productOptionBeans;
	private String createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public ProductTypeBean getProductTypeBean() {
		return productTypeBean;
	}

	public void setProductTypeBean(ProductTypeBean productTypeBean) {
		this.productTypeBean = productTypeBean;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSellNumber() {
		return sellNumber;
	}

	public void setSellNumber(int sellNumber) {
		this.sellNumber = sellNumber;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getProductProperties() {
		return productProperties;
	}

	public void setProductProperties(String productProperties) {
		this.productProperties = productProperties;
	}

	public List<ProductOptionBean> getProductOptionBeans() {
		return productOptionBeans;
	}

	public void setProductOptionBeans(List<ProductOptionBean> productOptionBeans) {
		this.productOptionBeans = productOptionBeans;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public ProductBean() {
	}

	public ProductBean(int id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public ProductBean(int id, String name, float originalPrice, float price, String intro, int typeId, int number,
			int sellNumber, String pic, String productProperties, String createDate) {
		this.setId(id);
		this.setName(name);
		this.setOriginalPrice(originalPrice);
		this.setPrice(price);
		this.setIntro(intro);
		this.setProductTypeId(typeId);
		this.setNumber(number);
		this.setSellNumber(sellNumber);
		this.setPic(pic);
		this.setProductProperties(productProperties);
		this.setCreateDate(createDate);
	}

	public ProductBean(int id, String name, float price, int number, String pic) {
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
		this.setNumber(number);
		this.setPic(pic);
	}
}
