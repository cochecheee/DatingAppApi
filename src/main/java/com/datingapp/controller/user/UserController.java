package com.datingapp.controller.user;

import org.springframework.web.bind.annotation.GetMapping;

public class UserController {
	// return user details include: id, username, email.,...
	@GetMapping("")
	public String getUserDetails() {
		return "";
	}
}
