package com.datingapp.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datingapp.service.ConversationService;


@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {
	@Autowired
	private ConversationService convService;
	// TODO 1: create chat with another user by userID
	@PostMapping("/{user2ID}")
	public ResponseEntity<?> createChatWithAnotherUser(@PathVariable("user2ID") String user2ID) {
		
		return ResponseEntity.ok(convService.createChat(user2ID));
	}
	
	// TODO 2: 
}
