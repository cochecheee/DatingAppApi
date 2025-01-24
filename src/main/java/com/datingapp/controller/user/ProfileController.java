package com.datingapp.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.datingapp.dto.request.ProfileRequest;
import com.datingapp.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            return ResponseEntity.ok(profileService.getProfileByUsername(username));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
	}

	/*
	 * PUT /profiles
        - Update an existing user profile
        - Allow updating photo, interests, etc.
	 * */
	@PutMapping("/profiles")
	public ResponseEntity<?> updateProfile(@RequestBody ProfileRequest request, @RequestHeader("Authorization") String token) {
		try {
			return ResponseEntity.ok(profileService.updateProfile(request, token));
		} catch (ResponseStatusException ex) {
			return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
		}
	}
	
}
