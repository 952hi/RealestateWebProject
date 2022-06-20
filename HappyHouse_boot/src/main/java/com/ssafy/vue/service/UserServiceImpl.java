package com.ssafy.vue.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.UserInfoDto;
import com.ssafy.vue.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private UserMapper UserMapper;

	@Autowired
	public UserServiceImpl(UserMapper UserMapper) {
		this.UserMapper = UserMapper;
	}

	@Override
	public int findpw(UserInfoDto userInfoDto) throws SQLException {
		return UserMapper.findpw(userInfoDto);
	}

	@Override
	public int DeleteUser(String id) throws SQLException {
		return UserMapper.DeleteUser(id);
	}

	@Override
	public UserInfoDto GetUser(UserInfoDto userInfoDto) throws SQLException {
		return UserMapper.GetUser(userInfoDto);
	}

	@Override
	public int IdCheck(String id) throws SQLException {
		return UserMapper.IdCheck(id);
	}

	@Override
	public List<UserInfoDto> ListAllUser() throws SQLException {
		return UserMapper.ListAllUser();
	}

//	@Override
//	public UserInfoDto Login(UserInfoDto userInfoDto) throws SQLException {
//		return UserMapper.GetUser(userInfoDto);
//	}

	@Override
	public int RegisterMember(UserInfoDto userInfoDto) throws SQLException {
		return UserMapper.RegisterMember(userInfoDto);
	}

	@Override
	public int UpdateUser(UserInfoDto userInfoDto) throws SQLException {
		return UserMapper.UpdateUser(userInfoDto);

	}

	@Override
	public int setPw(UserInfoDto userInfoDto) {
		return UserMapper.setPw(userInfoDto);
	}
}
