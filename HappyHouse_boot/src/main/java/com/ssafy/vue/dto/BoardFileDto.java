package com.ssafy.vue.dto;

public class BoardFileDto {
	private int no;
	private int board_no;
	private String original_name;
	private String save_path;
	private long size;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public String getOriginal_name() {
		return original_name;
	}

	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}

	public String getSave_path() {
		return save_path;
	}

	public void setSave_path(String save_path) {
		this.save_path = save_path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "BoardFileDto [no=" + no + ", board_no=" + board_no + ", original_name=" + original_name + ", save_path="
				+ save_path + ", size=" + size + "]";
	}

}
