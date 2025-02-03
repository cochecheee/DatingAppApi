package com.datingapp.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.datingapp.dto.ErrorResponse;
import com.datingapp.dto.matching.CardResponse;
import com.datingapp.entity.UserAccount;
import com.datingapp.exception.ErrorMessage;
//import com.datingapp.repository.IMatchingRepository;
import com.datingapp.repository.IUserAccountRepository;
import com.datingapp.service.MatchingService;

@Service
public class MatchServiceImpl implements MatchingService {
	@Autowired
	private IUserAccountRepository userRepository;
//	@Autowired
//	private IMatchingRepository matchingRepository;
	
	// get Card for user
	@Override
	public List<?> getCardForUser() {
		// get current user
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserAccount> currentUser = userRepository.findByEmail(username);
		if(currentUser.isEmpty()) {
			return  List.of(ErrorResponse.builder()
					.message(ErrorMessage.USER_NOT_EXIST)
					.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
					.build());
		}
		
		// already match with this user -> pass
		List<CardResponse> listCard = new ArrayList<>();
		List<UserAccount> listAccount = userRepository.findAllUsersNotMatchedWith(currentUser.get().getId());
		
		// initialize card
		for(UserAccount acc : listAccount) {
			CardResponse res = CardResponse.builder()
					.user_id(acc.getId())
					.user_gender(acc.getGender().getName())
					.user_image("") //solving user photo problem
					.user_bio(acc.getDetails())
					.user_name(acc.getFirstName() + " " + acc.getLastName())
					.build();
			listCard.add(res);
		}
		return listCard;
	}
}
