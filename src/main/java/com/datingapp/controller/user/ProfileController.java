package com.datingapp.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.datingapp.dto.response.ProfileResponse;
import com.datingapp.service.ProfileService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v1/user")
public class ProfileController {
	@Autowired
	private ProfileService profileService;
	
	// TODO 2: check after do TODO 1
	@GetMapping("/profiles")
	public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
		try {
            // get username from jwt token
            String username = profileService.extractUsernameFromToken(token);
            // get profile by username (email)
            ProfileResponse profile = profileService.getProfileByUsername(username);
            return ResponseEntity.ok(profile);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
	}

	/*
	 * PUT /profiles
        - Update an existing user profile
        - Allow updating photo, interests, etc.
	 * */
//	@PutMapping("/profiles")
	
}
