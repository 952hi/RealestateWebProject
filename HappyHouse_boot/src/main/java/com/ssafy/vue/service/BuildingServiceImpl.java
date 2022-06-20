package com.ssafy.vue.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.AroundbusinessareaDto;
import com.ssafy.vue.dto.DongcodeDto;
import com.ssafy.vue.dto.GuguncodeDto;
import com.ssafy.vue.dto.HousedealDto;
import com.ssafy.vue.dto.HouseinfoDto;
import com.ssafy.vue.dto.SidocodeDto;
import com.ssafy.vue.dto.SubwayDto;
import com.ssafy.vue.mapper.BuildingMapper;


@Service
public class BuildingServiceImpl implements BuildingService {
	
	@Autowired
	private BuildingMapper mapper;
	
	@Override
	public List<SidocodeDto> selectSi() throws Exception {
		return mapper.selectSi();
	}

	@Override
	public List<GuguncodeDto> selectGu(String si) throws Exception {
		return mapper.selectGu(si);
	}
	
	@Override
	public List<AroundbusinessareaDto> around() throws Exception {
		return mapper.around();
	}

	@Override
	public List<DongcodeDto> selectDong(String gu) throws Exception {
		return mapper.selectDong(gu);
	}

	@Override
	public List<HouseinfoDto> selectApt(int code,int termlow,int termhigh) throws Exception {
		return mapper.selectApt(code,termlow,termhigh);
	}

	@Override
	public List<SubwayDto> selectSubway() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectSubway();
	}

	@Override
	public List<HousedealDto> dealsearch(String code) throws Exception {
		return mapper.dealsearch(code);
	}
}
