package com.ssafy.vue.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vue.dto.ChatHistoryDto;
import com.ssafy.vue.dto.ChatRoomDto;
import com.ssafy.vue.service.ChatService;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ChatWebSocketHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

	@Autowired
	private ChatService chatService;

	private final Map<String, WebSocketSession> webSocketSessions = new HashMap<>();
	private final Map<WebSocketSession, String> webSocketIds = new HashMap<>();

	// 처음 연결 시도될 때
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("afterConnectionEstablished session : {}", session);
		System.out.println("연결 시작===================================================");
		System.out.println("session : "+session);
		System.out.println("===================================================");
	}

	// 클라이언트로부터 메세지를 송신했을 때
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("메세지 송신===================================================");
		System.out.println("session :" + session);
		System.out.println("message.getPayload().toString() : " + message.getPayload().toString());
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = objectMapper.readValue(message.getPayload().toString(),
				new TypeReference<Map<String, Object>>() {
				});
		// 첫 세션 등록 메세지일 경우
		if (jsonMap.get("setSession") != null) {
			webSocketSessions.put((String) jsonMap.get("id"), session);
			webSocketIds.put(session, (String) jsonMap.get("id"));
		}
		// 송신 메세지일 경우
		else {
			// DB에 채팅 기록을 저장
			ChatHistoryDto chatHistoryDto = new ChatHistoryDto();
			chatHistoryDto.setId((String) jsonMap.get("sender"));
			chatHistoryDto.setRoom_no((Integer) jsonMap.get("room_no"));
			chatHistoryDto.setMessage((String) jsonMap.get("message"));
			chatService.InsertChatMessage(chatHistoryDto);
			
			//상대방의 alert를 on으로 변경
			ChatRoomDto chatRoomDto = new ChatRoomDto();
			chatRoomDto.setNo((Integer) jsonMap.get("room_no"));
			if((Integer)jsonMap.get("loginUserGrade") == 1) {
				chatRoomDto.setSeller_id((String) jsonMap.get("receiver"));
			}
			else {
				chatRoomDto.setUser_id((String) jsonMap.get("receiver"));
			}
			chatService.UpdateAlertOn(chatRoomDto);
			
			// 상대방의 세션 아이디를 검색
			WebSocketSession receiver = webSocketSessions.get((String) jsonMap.get("receiver"));
			//세션 아이디가 존재하면 메세지 전송
			if (receiver != null) {
				receiver.sendMessage(message);
			}
			//자기 자신에게 전송
			session.sendMessage(message);
		}
		System.out.println("===================================================");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("연결해제 ===================================================");
		System.out.println("session :" +session);
		System.out.println("session에 해당하는 ID : " + webSocketIds.get(session));
		
		String id = webSocketIds.get(session);
		webSocketSessions.remove(id);
		webSocketIds.remove(session);
		System.out.println("===================================================");
	}
}
