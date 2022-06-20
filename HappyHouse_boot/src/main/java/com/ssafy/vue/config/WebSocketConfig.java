package com.ssafy.vue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ssafy.vue.handler.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	// /chat으로 접속하면 echoHandler가 처리
	private final static String CHAT_ENDPOINT = "/livechat";

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(getChatWebSocketHandler(), CHAT_ENDPOINT).setAllowedOrigins("*");
	}

	@Bean
	public WebSocketHandler getChatWebSocketHandler() {
		return new ChatWebSocketHandler();
	}
}
