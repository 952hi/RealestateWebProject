package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.Board;

public interface ObserveService {
	public List<String> selectSido();
	public List<String> selectGuDong(String Sido);
}
