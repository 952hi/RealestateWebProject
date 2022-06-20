package com.ssafy.vue.dto;

public class HouseinfoDto {
	private int aptCode;
	private String aptName;
	private String dongName;
	private String dongCode;
	private int buildYear;
	private String jibun;
	private String lat;
	private String lng;
	private String img;
	private String dealAmount;
	private int dealYear;
	public int getDealYear() {
		return dealYear;
	}
	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public int getAptCode() {
		return aptCode;
	}
	public void setAptCode(int aptCode) {
		this.aptCode = aptCode;
	}
	public int getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "HouseinfoDto [aptCode=" + aptCode + ", buildYear=" + buildYear + ", aptName=" + aptName + ", dongName="
				+ dongName + ", dongCode=" + dongCode + ", jibun=" + jibun + ", lat=" + lat + ", lng=" + lng + ", img="
				+ img + "]";
	}
}
