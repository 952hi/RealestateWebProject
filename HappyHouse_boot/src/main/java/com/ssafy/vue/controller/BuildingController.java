package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.AroundbusinessareaDto;
import com.ssafy.vue.dto.DongcodeDto;
import com.ssafy.vue.dto.GuguncodeDto;
import com.ssafy.vue.dto.HousedealDto;
import com.ssafy.vue.dto.HouseinfoDto;
import com.ssafy.vue.dto.SidocodeDto;
import com.ssafy.vue.dto.SubwayDto;
import com.ssafy.vue.service.BuildingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/Building")
public class BuildingController {
	
	@Autowired
	private BuildingService service;
	
	@ApiOperation(value = "시,도 정보를 조회한다.", response = List.class)
	@GetMapping("/Si")
	public @ResponseBody List<SidocodeDto> Si() throws Exception {
		List<SidocodeDto> list =  service.selectSi();
		return list;
	}
	
	@GetMapping("/Gu")
	@ApiOperation(value = "시,도 정보에 맞는 구군 정보를 조회한다.", response = List.class)
	public @ResponseBody List<GuguncodeDto> Gu(@RequestParam("sido") String sido) throws Exception {
		List<GuguncodeDto> list =  service.selectGu(sido);
		return list;
	}
	@GetMapping("/Dong")
	@ApiOperation(value = "시,도,구,군 정보에 맞는 동 정보를 조회한다.", response = List.class)
	public @ResponseBody List<DongcodeDto> Dong(@RequestParam("gugun") String gu) throws Exception {
		List<DongcodeDto> list =  service.selectDong(gu);
		return list;
	}
	
	@GetMapping("/around")
	@ApiOperation(value = "주변상권 정보를 조회한다", response = List.class)
	public @ResponseBody List<AroundbusinessareaDto> around() throws Exception{
		List<AroundbusinessareaDto> list = service.around();
		return list;
	}
	
	@GetMapping("/House")
	@ApiOperation(value = "동코드에 맞는 아파트 거래 정보를 조회한다", response = List.class)
	public @ResponseBody List<HouseinfoDto> Apt(@RequestParam HashMap<String, String> map ) throws Exception {
		int code = Integer.parseInt(map.get("dong"));
		int minval = Integer.parseInt(map.get("minval"));
		int maxval = Integer.parseInt(map.get("maxval"));
		int termlow = Integer.parseInt(map.get("termlow"));
		int termhigh = Integer.parseInt(map.get("termhigh"));
		int tradehigh = Integer.parseInt(map.get("tradehigh"));
		int tradelow = Integer.parseInt(map.get("tradelow"));
		System.out.println(map.toString());
		List<HouseinfoDto> list =  service.selectApt(code,termlow,termhigh);
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getDealAmount()==null) continue;
			String chk = list.get(i).getDealAmount().trim().replaceAll(",", "");
			int year = list.get(i).getDealYear();
			int val = Integer.parseInt(chk);
			System.out.println(val+" minval "+minval+" max "+maxval);
			if(val<minval || val>maxval || year<tradelow || year>tradehigh) {
				list.remove(i);
				i--;
			}
		}
		System.out.println(list);
		return list;
	}
	@GetMapping("/Subway")
	@ApiOperation(value = "서울시 지하철 위치정보 조회", response = List.class)
	public @ResponseBody List<SubwayDto> Subway() throws Exception {
		List<SubwayDto> list =  service.selectSubway();
		return list;
	}
	@GetMapping("/Deal")
	@ApiOperation(value = "아파트코드별 거래내역 히스토리 조회", response = List.class)
	public @ResponseBody List<HousedealDto> dealsearch(@RequestParam("code") String code) throws Exception {
		List<HousedealDto> list =  service.dealsearch(code);
		return list;
	}
}
