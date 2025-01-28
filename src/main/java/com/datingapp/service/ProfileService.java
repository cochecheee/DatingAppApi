package com.datingapp.service;

import com.datingapp.dto.profile.ProfileRequest;

public interface ProfileService {
	Object getProfileByUsername(String username);
	Object updateProfile(ProfileRequest profile);
}
