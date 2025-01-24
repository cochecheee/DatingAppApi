package com.datingapp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datingapp.configs.jwt.JwtService;
import com.datingapp.dto.response.ProfileResponse;
import com.datingapp.entity.UserAccount;
import com.datingapp.service.ProfileService;
import com.datingapp.service.UserAccountService;

@Service
public class ProfileServiceImpl implements ProfileService{
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserAccountService userService;
	
	@Override
	public String extractUsernameFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or missing token");
        }
        String jwtToken = token.substring(7);

        try {
            return jwtService.extractUsername(jwtToken);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }
	
	@Override
	public ProfileResponse getProfileByUsername(String username) {
		Optional<UserAccount> user = userService.findByEmail(username);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserAccount u = user.get();
        return ProfileResponse.builder()
                .email(u.getEmail())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .gender(u.getGender().getName())
                .polarity(u.getPolarity())
                .nickname(u.getNickname())
                .details(u.getDetails())
                .build();
	}
}
