package com.oracle.jsp.bean;

public class AddressBean {
	private int id;
	private String name;
	private int province;
	private int city;
	private int region;
	private String address;
	private String cellphone;
	private int user_id;
	private int status;
	private String create_date;
	private String provincename;//省份名
	private String cityname;//城市名
	private String areaname;//地方名
	
	

	public AddressBean(int id,String name,int province,int city,int region,String address,String cellphone,int user_id,int status,String create_date){
		this.setId(id);
		this.setName(name);
		this.setProvince(province);
		this.setCity(city);
		this.setRegion(region);
		this.setAddress(address);
		this.setCellphone(cellphone);
		this.setUser_id(user_id);
		this.setStatus(status);
		this.setCreate_date(create_date);
	}
	

	public String getProvincename() {
		return provincename;
	}


	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}


	public String getCityname() {
		return cityname;
	}


	public void setCityname(String cityname) {
		this.cityname = cityname;
	}


	public String getAreaname() {
		return areaname;
	}


	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}


	public AddressBean(){
		
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
	public int getProvince() {
		return province;
	}
	public void setProvince(int province) {
		this.province = province;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
