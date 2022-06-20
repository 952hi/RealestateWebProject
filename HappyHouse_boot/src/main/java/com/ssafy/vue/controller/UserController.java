package com.ssafy.vue.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.UserInfoDto;
import com.ssafy.vue.service.UserService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserService userService;

//	회원 목록을 가지고 userlist로 이동
	@GetMapping("/userlist")
	public ResponseEntity<List<UserInfoDto>> userinfo() throws SQLException {
		List<UserInfoDto> userList = userService.ListAllUser();
		return new ResponseEntity<List<UserInfoDto>>(userList, HttpStatus.OK);
	}

//	회원 목록을 가지고 userlist로 이동
	@PostMapping("/login")
	public ResponseEntity<UserInfoDto> login(@RequestBody HashMap<String, Object> map) throws SQLException {
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setId((String) map.get("id"));
		userInfoDto.setPw((String) map.get("pw"));

		UserInfoDto user = userService.GetUser(userInfoDto);
		return new ResponseEntity<UserInfoDto>(user, HttpStatus.OK);
	}

	@PostMapping("/regist")
	public ResponseEntity<String> RegisterMember(@RequestBody HashMap<String, Object> map) throws SQLException {
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setId((String) map.get("id"));
		userInfoDto.setPw((String) map.get("pw"));
		userInfoDto.setEmail((String) map.get("email"));
		userInfoDto.setAge((Integer) map.get("age"));
		userInfoDto.setName((String) map.get("name"));
		try {
			int rslt = userService.RegisterMember(userInfoDto);
			if (rslt == 1)
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			else
				return new ResponseEntity<String>(FAIL, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.CREATED);
		}
	}

//	@GetMapping("/userinfo")
//	public ResponseEntity<UserInfoDto> userinfo(HttpSession session) throws SQLException {
//		return new ResponseEntity<UserInfoDto>(user, HttpStatus.OK);
//	}

	@PostMapping("/UpdateUser")
	public ResponseEntity<String> UpdateUser(UserInfoDto userInfoDto) throws SQLException {
		logger.debug("userInfoDto : {}", userInfoDto);

		int rslt = userService.UpdateUser(userInfoDto);
		if (rslt == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			logger.debug("수정 실패, {}", rslt);
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping("findPw")
	public ResponseEntity<String> findPw(@RequestBody UserInfoDto userInfoDto) throws SQLException {
		System.out.println(userInfoDto);
		logger.debug("userInfoDto : {}", userInfoDto);

		int rslt = userService.findpw(userInfoDto);
		if (rslt == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			logger.debug("수정 실패, {}", rslt);
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PostMapping("setPw")
	public ResponseEntity<String> setPw(@RequestBody UserInfoDto userInfoDto) throws SQLException {
		logger.debug("userInfoDto : {}", userInfoDto);

		int rslt = userService.setPw(userInfoDto);
		if (rslt == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			logger.debug("수정 실패, {}", rslt);
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PutMapping("{id}")
	public ResponseEntity<String> UpdateUser(@RequestBody HashMap<String, Object> map) throws Exception {
		System.out.println(map.toString());
		System.out.println("접속 ok");

		UserInfoDto userInfoDto = new UserInfoDto();

		userInfoDto.setPw((String) map.get("pw"));
		userInfoDto.setId((String) map.get("id"));
		userInfoDto.setEmail((String) map.get("email"));
		userInfoDto.setAge((Integer) map.get("age"));

		logger.debug("userInfoDto : {}", userInfoDto);
		int rslt = userService.UpdateUser(userInfoDto);
		if (rslt == 1)
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}

	// 비동기로 데이터 전송
//	@PostMapping("/getuser")
//	public String GetUser(String id) throws SQLException {
//		userService.GetUser(id);
//		return "Main";
//	}

	// 비동기로 데이터 전송
	@PostMapping("/getlist")
	public String getlist(String id, HttpSession session) throws SQLException {
		// 추후 기능구현
		return "";
	}

	// 회원 자발적 탈퇴+
	@DeleteMapping("{id}")
	public ResponseEntity<String> withdrawl(@PathVariable String id) throws SQLException {
		int rslt = userService.DeleteUser(id);
		if (rslt == 1)
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}

	// 관리자에 의한 삭제
	@DeleteMapping("/delete/{id}")
	public void DeleteUser(@PathVariable("id") String id, HttpServletResponse response)
			throws SQLException, IOException {
//		추후 작성
	}

	@GetMapping("/logout")
	public String Logout(HttpServletRequest request) {
		// 추후 기능구현
		return "Main";
	}
}
