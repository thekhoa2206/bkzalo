package com.web.controller.user;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import com.web.entities.ChatMessage;

public class MessageController {


	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/publicChatRoom")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/publicChatRoom")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// Thêm Phone vào web socket session
		headerAccessor.getSessionAttributes().put("Phone", chatMessage.getUserSender());
		return chatMessage;
	}
}
