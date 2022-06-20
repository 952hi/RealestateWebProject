package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.ChatHistoryDto;
import com.ssafy.vue.dto.ChatRoomDto;
import com.ssafy.vue.service.ChatService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/chat")
public class ChatController {
	private static final Logger logger = LoggerFactory.getLogger(ObserveController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ChatService chatService;

	@ApiOperation(value = "상담 버튼 클릭 시 채팅방 개설 및 알람 설정", response = String.class)
	@PostMapping
	public ResponseEntity<String> GenerateRoom(@RequestBody HashMap<String, Object> map) throws Exception {
		System.out.println(map.toString());

		ChatRoomDto chatRoomDto = new ChatRoomDto();
		chatRoomDto.setUser_id((String) map.get("user_id"));
		chatRoomDto.setSeller_id((String) map.get("seller_id"));

		// 이미 방이 개설되었는지 확인
		int rslt = chatService.SelectOneRoom(chatRoomDto);
		System.out.println(rslt);
		if (rslt == 0) {
			chatService.GenerateRoom(chatRoomDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else
			return new ResponseEntity<String>(FAIL,HttpStatus.OK);
	}

	@GetMapping("{id}/{grade}")
	public ResponseEntity<List<ChatRoomDto>> LoadChatRoomList(@PathVariable String id, @PathVariable int grade)
			throws Exception {
		System.out.println("LoadChatRoomList, id : " + id + ", grade : " + grade);
		List<ChatRoomDto> list = null;
		ChatRoomDto chatRoomDto = new ChatRoomDto();
		if (grade == 1) {
			chatRoomDto.setUser_id(id);
		} else {
			System.out.println(chatRoomDto.toString());
			chatRoomDto.setSeller_id(id);
		}
		
		list = chatService.LoadChatRoomList(chatRoomDto);

		return new ResponseEntity<List<ChatRoomDto>>(list, HttpStatus.OK);
	}

	@GetMapping("{room_no}")
	public ResponseEntity<List<ChatHistoryDto>> LoadChatHistory(@PathVariable int room_no) throws Exception {
		System.out.println("room_no : " + room_no);
		List<ChatHistoryDto> chatHistoryList = chatService.GetRoomChatHistory(room_no);
		return new ResponseEntity<List<ChatHistoryDto>>(chatHistoryList, HttpStatus.OK);
	}

	@PutMapping("off")
	public ResponseEntity<String> UpdateAlertOff(@RequestBody HashMap<String, Object> map) throws Exception {
		System.out.println("RUN UpdateChatRoomAlert");
		ChatRoomDto chatRoomDto = new ChatRoomDto();
		chatRoomDto.setNo((Integer) map.get("no"));
		// 일반회원이라 면
		if ((Integer) map.get("grade") == 1) {
			chatRoomDto.setUser_id((String) map.get("id"));
		} else {
			chatRoomDto.setSeller_id((String) map.get("id"));
		}

		int rslt = chatService.UpdateAlertOff(chatRoomDto);
		if (rslt == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
//	채팅방 클릭 시 알람 제거

}
