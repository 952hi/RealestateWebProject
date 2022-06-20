package com.ssafy.vue.dto;

public class SubwayDto {
	private int no;
	private String line;
	private String name;
	private String lat;
	private String lng;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@Override
	public String toString() {
		return "SubwayDto [no=" + no + ", line=" + line + ", name=" + name + ", lat=" + lat + ", lng=" + lng + "]";
	}
}