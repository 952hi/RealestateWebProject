package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.ChatHistoryDto;
import com.ssafy.vue.dto.ChatRoomDto;

public interface ChatService {
	public int GenerateRoom(ChatRoomDto chatRoomDto);

	public int UpdateAlertOn(ChatRoomDto chatRoomDto);

	public int UpdateAlertOff(ChatRoomDto chatRoomDto);

	public int SelectOneRoom(ChatRoomDto chatRoomDto);

	public List<ChatRoomDto> LoadChatRoomList(ChatRoomDto chatRoomDto);

	public List<ChatHistoryDto> GetRoomChatHistory(int room_no);

	public int DeleteRoom(int no);

	public int InsertChatMessage(ChatHistoryDto chatHistoryDto);
}
