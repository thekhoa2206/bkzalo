package com.web.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.web.entities.ChatMessage;

@Component
public class WebSocketEventListener {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	@Autowired
	public UserService userService;
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	// Client gửi: Tôi vẫn đang kết nối
	@EventListener
	public void available(SessionConnectedEvent event) {
		logger.info("Received a new web socket connection");
	}

	// Client gửi: báo ngắt kết nối (thoát cửa sổ chat)
	@EventListener
	public void disconnect(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String phone = (String) headerAccessor.getSessionAttributes().get("phone");
		if (phone != null) {
			logger.info("User Disconnected : " + phone);
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setUserSender(userService.findUserByPhone(phone));
			messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
		}
	}

//	// Client gửi lên: User bắt đầu vào phòng chat
//	@EventListener
//	public void joinchat() {
//
//	}
//
//	// Server trả về: Hết thời gian đợi mà không kết nối được để vào phòng chat
//	@EventListener
//	public void connection_timeout() {
//
//	}
//
//	// Server báo: Không thể kết nối với phía bên kia
//	@EventListener
//	public void connection_error() {
//
//	}
//
//	// Client báo: Đang tạo lại kết nối
//	@EventListener
//	public void reconnecting() {
//
//	}
//
//	// Server báo: Không thể kết nối lại
//	@EventListener
//	public void reconnect_attempt() {
//
//	}
//
//	// Server báo: Có tin nhắn từ một trong hai người
//	@EventListener
//	public void onmessage() {
//
//	}
//
//	// client gửi: Yêu cầu thu hồi tin nhắn
//	@EventListener
//	public void deletemessage() {
//
//	}
//
//	// Client gửi: Gửi tin nhắn đến người còn lại
//	@EventListener
//	public void send() {
//
//	}
}
