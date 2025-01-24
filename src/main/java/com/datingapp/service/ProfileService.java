package com.datingapp.service;

import com.datingapp.dto.response.ProfileResponse;

public interface ProfileService {
	ProfileResponse getProfileByUsername(String username);
	String extractUsernameFromToken(String token);
}
