package com.datingapp.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datingapp.dto.chat.MessageRequest;
import com.datingapp.service.MessageService;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
	// TODO: add logic for expired JWT token
	
	@Autowired
	private MessageService messageService;
	
	// TODO 1: get mesages between 2 users
	@GetMapping("/{user1}/chat/{user2}")
	public ResponseEntity<?> getMessagesOfTwoUsers(@PathVariable("user1") String user1ID, @PathVariable("user2") String user2ID) {
		return ResponseEntity.ok(messageService.getMessagesOfTwoUsers(user1ID,user2ID));
	}
	
	// TODO 2: sending a message
	@PostMapping("/{user1}/send/{user2}")
	public ResponseEntity<?> sendingMessage(@PathVariable("user1") String user1, @PathVariable("user2") String user2, @RequestBody MessageRequest message) {
		return ResponseEntity.ok(messageService.sendingMessageToUser(user1,user2,message));
	}
	
	// TODO 3: delete a message
	
}
