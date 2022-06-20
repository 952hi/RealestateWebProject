package com.ssafy.vue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.dto.Board;
@Mapper
public interface ObserveMapper {
	public List<String> selectSido();
	public List<String> selectGuDong(String Sido);
}