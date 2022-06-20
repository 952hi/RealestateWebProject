package com.ssafy.vue.dto;

public class AroundbusinessareaDto {
	private int no;
	private String businessName;
	private String businessType;
	private String address;
	private String let;
	private String lng;
	private String dongCode;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLet() {
		return let;
	}
	public void setLet(String let) {
		this.let = let;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	@Override
	public String toString() {
		return "AroundbusinessareaDto [no=" + no + ", businessName=" + businessName + ", businessType=" + businessType
				+ ", address=" + address + ", let=" + let + ", lng=" + lng + ", dongCode=" + dongCode + "]";
	}
}
