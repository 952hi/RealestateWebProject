package com.ssafy.vue.dto;

import java.util.List;

public class TradeThreadDto {
	public int boardNo;
	public int contractOpt;
	public int deposit;
	public int monthlyFee;
	public int commonMaintainFee;
	public int loan;
	public String date;
	public String detail;
	public String roadnameAddress;
	public String detailAddress;
	public List<String> commonMaintainItem;
	public List<String> eachFeeItem;

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getContracOpt() {
		return contractOpt;
	}

	public void setContracOpt(int contractOpt) {
		this.contractOpt = contractOpt;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public int getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(int monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public int getCommonMaintainFee() {
		return commonMaintainFee;
	}

	public void setCommonMaintainFee(int commonMaintainFee) {
		this.commonMaintainFee = commonMaintainFee;
	}

	public int getLoan() {
		return loan;
	}

	public void setLoan(int loan) {
		this.loan = loan;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getRoadnameAddress() {
		return roadnameAddress;
	}

	public void setRoadnameAddress(String roadnameAddress) {
		this.roadnameAddress = roadnameAddress;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public List<String> getCommonMaintainItem() {
		return commonMaintainItem;
	}

	public void setCommonMaintainItem(List<String> commonMaintainItem) {
		this.commonMaintainItem = commonMaintainItem;
	}

	public List<String> getEachFeeItem() {
		return eachFeeItem;
	}

	public void setEachFeeItem(List<String> eachFeeItem) {
		this.eachFeeItem = eachFeeItem;
	}

	@Override
	public String toString() {
		return "TradeThreadDto [boardNo=" + boardNo + ", contractOpt=" + contractOpt + ", deposit=" + deposit
				+ ", monthlyFee=" + monthlyFee + ", commonMaintainFee=" + commonMaintainFee + ", loan=" + loan
				+ ", date=" + date + ", detail=" + detail + ", roadnameAddress=" + roadnameAddress + ", detailAddress="
				+ detailAddress + ", commonMaintainItem=" + commonMaintainItem + ", eachFeeItem=" + eachFeeItem + "]";
	}

}
