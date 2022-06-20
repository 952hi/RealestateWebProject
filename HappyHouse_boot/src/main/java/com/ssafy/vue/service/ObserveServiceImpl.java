package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.vue.mapper.ObserveMapper;

@Service
public class ObserveServiceImpl implements ObserveService {

	@Autowired
	private ObserveMapper observeMapper;

	@Override
	public List<String> selectGuDong(String Sido) {
		return observeMapper.selectGuDong(Sido);
	}

	@Override
	public List<String> selectSido() {
		return observeMapper.selectSido();
	}
}