package com.ssafy.vue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.dto.ChatHistoryDto;
import com.ssafy.vue.dto.ChatRoomDto;

@Mapper
public interface ChatMapper {
	public int GenerateRoom(ChatRoomDto chatRoomDto);

	public int UpdateAlertOn(ChatRoomDto chatRoomDto);

	public int UpdateAlertOff(ChatRoomDto chatRoomDto);

	public int SelectOneRoom(ChatRoomDto chatRoomDto);

	public List<ChatRoomDto> LoadChatRoomList(ChatRoomDto chatRoomDto);

	public List<ChatHistoryDto> GetRoomChatHistory(int room_no);

	public int DeleteRoom(int no);

	public int InsertChatMessage(ChatHistoryDto chatHistoryDto);
}
