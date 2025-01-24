package com.datingapp.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datingapp.configs.jwt.JwtService;
import com.datingapp.dto.ErrorResponse;
import com.datingapp.dto.request.ProfileRequest;
import com.datingapp.dto.response.ProfileResponse;
import com.datingapp.entity.Gender;
import com.datingapp.entity.UserAccount;
import com.datingapp.exception.ErrorMessage;
import com.datingapp.repository.IGenderRepository;
import com.datingapp.repository.IUserAccountRepository;
import com.datingapp.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{
	@Autowired
	private JwtService jwtService;
	@Autowired
	private IUserAccountRepository userRepository;
	@Autowired IGenderRepository genderRepository;
	
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
	public Object getProfileByUsername(String username) {
		Optional<UserAccount> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            return ErrorResponse.builder()
            		.message("User not found.")
            		.status(404)
            		.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
            		.build();
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
	
	/* can update: lastname, firstname, nickname, gender, details*/
	@Override
	public Object updateProfile(ProfileRequest profile, String token) {
		// get username from token
		String username = extractUsernameFromToken(token);
		
		//find user by username (email)
		Optional<UserAccount> user = userRepository.findByEmail(username);
		if (user.isEmpty()) {
            return ErrorResponse.builder()
            		.message("User not found.")
            		.status(404)
            		.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
            		.build();
        }
		
		// extract data from ProfileResponse
		String newLastName = profile.getLastName();
		String newFirstName = profile.getFirstName();
		String newNickName = profile.getNickname();
		// TODO 1: check whether nickname existed
		Optional<UserAccount> existNickname = userRepository.findByNickname(newNickName);
		if (existNickname.isEmpty()) {
            return ErrorResponse.builder()
            		.message(ErrorMessage.NICKNAME_EXIST)
            		.status(404)
            		.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
            		.build();
        }
		// TODO 2: check whether gender valid
		Optional<Gender> gender = genderRepository.findById(profile.getGender());
		if(gender.isEmpty()) {
			return ErrorResponse.builder()
            		.message(ErrorMessage.GENDER_INVALID)
            		.status(404)
            		.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
            		.build();

		}
		String details = profile.getDetails();
		
		// update data
		user.get().setLastName(newLastName);
		user.get().setFirstName(newFirstName);
		user.get().setDetails(details);
		user.get().setNickname(newNickName);
		user.get().setGender(gender.get());
		
		//save user
		userRepository.save(user.get());
		
		return ProfileResponse.builder()
				.email(profile.getEmail())
				.polarity(user.get().getPolarity())
				.nickname(newNickName)
				.lastName(newLastName)
				.firstName(newFirstName)
				.details(details)
				.gender(profile.getGender())
				.build();
	}
}
