package com.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.entities.AjaxResponse;
import com.web.entities.ChatMessage;
import com.web.services.ChatService;

@RestController
public class ChatController {

	@Autowired
	ChatService chatService;

	@PostMapping(value = { "/get_list_conversation/{user_id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_list_conversation(@PathVariable("user_id") int userId, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		List<ChatMessage> data = chatService.findChatByUserId(userId);

		int numNewMessage = 0;
		for (ChatMessage chat : data) {
			if (chat.getUnread() == true)
				numNewMessage++;
		}

		return ResponseEntity.ok(new AjaxResponse(200, "OK!", data));
	}

	@PostMapping(value = { "/{user_id}/get_conversation/{user_partner_id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_conversation(@PathVariable("user_id") int userId,
			@PathVariable("user_partner_id") int partnerId, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {
		List<ChatMessage> data = chatService.findChatByUserIdAndPartnerId(userId, partnerId);
		return ResponseEntity.ok(new AjaxResponse(200, "OK!", data));
	}

	@PostMapping(value = { "/{user_id}/delete_message/{user_partner_id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> delete_message(@PathVariable("user_id") int userId,
			@PathVariable("user_partner_id") int partnerId, @RequestBody ChatMessage chat, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		chatService.deleteMessage(chat.getId(), userId);
		ChatMessage data = null;
		return ResponseEntity.ok(new AjaxResponse(200, "OK!", data));
	}

	@PostMapping(value = { "/{user_id}/delete_conversation/{user_partner_id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> delete_conversation(@PathVariable("user_id") int userId,
			@PathVariable("user_partner_id") int partnerId, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {
		chatService.deleteConversation(userId, partnerId);
		ChatMessage data = null;
		return ResponseEntity.ok(new AjaxResponse(200, "OK!", data));
	}
}
