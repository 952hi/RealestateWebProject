package com.ssafy.vue.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Board (게시글정보)", description = "글번호, 제목, 내용, 작성자아이디, 작성일을 가진   Domain Class")
public class Board {
	@ApiModelProperty(value = "글번호")
	private int no;
	@ApiModelProperty(value = "작성자아이디")
	private String id;
	@ApiModelProperty(value = "제목")
	private String title;
	@ApiModelProperty(value = "내용")
	private String content;
	@ApiModelProperty(value = "조회수")
	private int hit;
	@ApiModelProperty(value = "작성일")
	private String time;
	@ApiModelProperty(value = "게시판구분")
	private String opt;
	@ApiModelProperty(value = "판매아파트주소")
	private String address;
	@ApiModelProperty(value = "게시글 파일 목록")
	private List<BoardFileDto> fileList;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<BoardFileDto> getFileList() {
		return fileList;
	}

	public void setFileList(List<BoardFileDto> fileList) {
		this.fileList = fileList;
	}

	@Override
	public String toString() {
		return "Board [no=" + no + ", id=" + id + ", title=" + title + ", content=" + content + ", hit=" + hit
				+ ", time=" + time + ", opt=" + opt + ", address=" + address + ", fileList=" + fileList + "]";
	}

}