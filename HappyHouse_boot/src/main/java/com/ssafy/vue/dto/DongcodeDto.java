package com.ssafy.vue.dto;

public class DongcodeDto {
	private String dongCode;
	private String sidoName;
	private String gugunName;
	private String dongName;
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	@Override
	public String toString() {
		return "DongcodeDto [dongCode=" + dongCode + ", sidoName=" + sidoName + ", gugunName=" + gugunName
				+ ", dongName=" + dongName + "]";
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	
}
