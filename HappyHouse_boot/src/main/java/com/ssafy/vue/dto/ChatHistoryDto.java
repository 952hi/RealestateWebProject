package com.ssafy.vue.dto;

public class ChatHistoryDto {
	private int no;
	private int room_no;
	private String id;
	private String message;
	private String date;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ChatHistoryDto [no=" + no + ", room_no=" + room_no + ", id=" + id + ", message=" + message + ", date="
				+ date + "]";
	}

}
