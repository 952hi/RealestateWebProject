package com.ssafy.vue.dto;

public class ChatRoomDto {
	private int no;
	private String user_id;
	private String seller_id;
	private String date;
	private int userside_alert;
	private int sellerside_alert;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUserside_alert() {
		return userside_alert;
	}

	public void setUserside_alert(int userside_alert) {
		this.userside_alert = userside_alert;
	}

	public int getSellerside_alert() {
		return sellerside_alert;
	}

	public void setSellerside_alert(int sellerside_alert) {
		this.sellerside_alert = sellerside_alert;
	}

	@Override
	public String toString() {
		return "ChatRoomDto [no=" + no + ", user_id=" + user_id + ", seller_id=" + seller_id + ", date=" + date
				+ ", userside_alert=" + userside_alert + ", sellerside_alert=" + sellerside_alert + "]";
	}

}
