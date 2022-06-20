package com.ssafy.vue.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.dto.UserInfoDto;


public interface UserService {
	// 가입
	public int RegisterMember(UserInfoDto userInfoDto) throws SQLException;

	// 아이디 검사
	public int IdCheck(String id) throws SQLException;

	// 아이디 로긴
//	public UserInfoDto Login(UserInfoDto userInfoDto) throws SQLException;

	// 전체 회원 조회
	public List<UserInfoDto> ListAllUser() throws SQLException;

	// 회원 한명 조회
	public UserInfoDto GetUser(UserInfoDto userInfoDto) throws SQLException;

	public int UpdateUser(UserInfoDto userInfoDto) throws SQLException;

	int DeleteUser(String id) throws SQLException;
	
	int findpw(UserInfoDto userInfoDto) throws SQLException;
	
	int setPw(UserInfoDto userInfoDto);
}
