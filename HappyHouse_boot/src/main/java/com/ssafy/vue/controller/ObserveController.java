package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.service.ObserveService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/observe")
public class ObserveController {

	private static final Logger logger = LoggerFactory.getLogger(ObserveController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ObserveService observeService;

    @ApiOperation(value = "시도 정보 반환", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<String>> selectSido() throws Exception {
//		logger.debug("retrieveBoard - 호출 {}" , observeService.selectSido().toString());
		return new ResponseEntity<List<String>>(observeService.selectSido(), HttpStatus.OK);
	}
    
    @ApiOperation(value = "구군동 정보 반환", response = List.class)
	@GetMapping("/gudong/{Sido}")
	public ResponseEntity<List<String>> selectGuDong(@PathVariable String Sido) throws Exception {
//		logger.debug("retrieveBoard - 호출 {}" , observeService.selectGuDong(Sido).toString());
		return new ResponseEntity<List<String>>(observeService.selectGuDong(Sido), HttpStatus.OK);
	}
}