package com.datingapp.controller.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageController {
	// TODO 1: get mesages between 2 users
	@GetMapping("/{user1}/chat/{user2}")
	public ResponseEntity<?> getMessagesOfTwoUsers(@PathVariable("user1") String user1ID, @PathVariable("user2") String user2ID) {
		return ResponseEntity.ok("ok");
	}
	
	// TODO 2: sending a message
	
	// TODO 3: delete a message
	
}
