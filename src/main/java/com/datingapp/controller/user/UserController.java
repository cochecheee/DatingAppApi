package com.datingapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import com.datingapp.dto.user.UserResponse;
import com.datingapp.entity.UserAccount;
import com.datingapp.service.UserAccountService;

public class UserController {
	@Autowired
	private UserAccountService userService;
	// return user details include: id, username, email.,...
	@GetMapping("")
	public ResponseEntity<?> getUserDetails() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserAccount acc = userService.findByEmail(username).get();
		return ResponseEntity.ok(UserResponse.builder()
						.userID(acc.getId())
						.username(acc.getNickname())
						.email(acc.getEmail())
						.build());
	}
}
