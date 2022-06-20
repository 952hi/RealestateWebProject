package com.ssafy.vue.dto;

public class HousedealDto {
	private int no;
	private int aptCode;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private int dealperSize;
	private String dealAmount;
	private String area;
	private String floor;
	private String type;
	private String rentMoney;
	private String msize;
	public String getMsize() {
		return msize;
	}
	public int getDealperSize() {
		return dealperSize;
	}
	public void setDealperSize(int dealperSize) {
		this.dealperSize = dealperSize;
	}
	public void setMsize(String msize) {
		this.msize = msize;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getAptCode() {
		return aptCode;
	}
	public void setAptCode(int aptCode) {
		this.aptCode = aptCode;
	}
	public int getDealYear() {
		return dealYear;
	}
	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}
	public int getDealMonth() {
		return dealMonth;
	}
	public void setDealMonth(int dealMonth) {
		this.dealMonth = dealMonth;
	}
	public int getDealDay() {
		return dealDay;
	}
	public void setDealDay(int dealDay) {
		this.dealDay = dealDay;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRentMoney() {
		return rentMoney;
	}
	public void setRentMoney(String rentMoney) {
		this.rentMoney = rentMoney;
	}
	@Override
	public String toString() {
		return "HousedealDto [no=" + no + ", aptCode=" + aptCode + ", dealYear=" + dealYear + ", dealMonth=" + dealMonth
				+ ", dealDay=" + dealDay + ", dealperSize=" + dealperSize + ", dealAmount=" + dealAmount + ", area="
				+ area + ", floor=" + floor + ", type=" + type + ", rentMoney=" + rentMoney + ", msize=" + msize + "]";
	}
}