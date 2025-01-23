package com.datingapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datingapp.configs.jwt.JwtService;
import com.datingapp.dto.response.ProfileResponse;
import com.datingapp.entity.UserAccount;
import com.datingapp.service.UserAccountService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v1/user")
public class ProfileController {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserAccountService userService;

	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getProfile(@PathVariable String id, @RequestHeader("Authorization") String token) {
		// Extract JWT token from the Authorization header
		if (token == null || !token.startsWith("Bearer ")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
	    }
	    String jwtToken = token.substring(7);
		
		// Extract username from the token
	    String username = "";
	    try {
	        username = jwtService.extractUsername(jwtToken);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	    }

		// Find the user in the database
	    Optional<UserAccount> user = userService.findByEmail(username);
	    if(user.isEmpty()) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }
	    UserAccount u = user.get();
	    
		// Check if the user ID from the token matches the requested user ID
	    if(!u.getId().equals(id)) {
	    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access this profile");
	    }

		// If they match, return the ProfileResponse
		ProfileResponse profile = ProfileResponse.builder()
				.email(u.getEmail())
				.firstName(u.getFirstName())
				.lastName(u.getLastName())
				.gender(u.getGender().getName())
				.polarity(u.getPolarity())
				.nickname(u.getNickname())
				.details(u.getDetails())
				.build();
		return ResponseEntity.ok(profile);

	}
}
