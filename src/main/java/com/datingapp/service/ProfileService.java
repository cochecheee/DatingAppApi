package com.datingapp.service;

import com.datingapp.dto.request.ProfileRequest;

public interface ProfileService {
	Object getProfileByUsername(String username);
	String extractUsernameFromToken(String token);
	Object updateProfile(ProfileRequest profile, String token);
}
