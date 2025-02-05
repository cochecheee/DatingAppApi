package com.datingapp.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datingapp.dto.ErrorResponse;
import com.datingapp.dto.matching.CardResponse;
import com.datingapp.entity.Conversation;
import com.datingapp.entity.Matching;
import com.datingapp.entity.Participant;
import com.datingapp.entity.UserAccount;
import com.datingapp.enums.RelationshipType;
import com.datingapp.exception.ErrorMessage;
import com.datingapp.repository.IConversationRepository;
import com.datingapp.repository.IParticipantRepository;
import com.datingapp.repository.IMatchingRepository;
import com.datingapp.repository.IUserAccountRepository;
import com.datingapp.service.MatchingService;

@Service
public class MatchServiceImpl implements MatchingService {
	@Autowired
	private IUserAccountRepository userRepository;
	@Autowired
	private IConversationRepository conversationRepository;
	@Autowired 
	private IParticipantRepository participantRepository;
	@Autowired
	private IMatchingRepository matchingRepository;
	
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
	
	@Override
	@Transactional
	public Object matching(String user1, String user2) {
		// TODO: check ID match current userID
		// find 2 user first
		UserAccount user1Account = userRepository.findById(user1).get();
		UserAccount user2Account = userRepository.findById(user2).get();
		
		// create conversation between 2 users
		//// create conv
		Conversation newConversation = Conversation.builder()
									.timeStarted(LocalDateTime.now())
									.timeClosed(null)
									.userAccount(user1Account)
									.build();
		conversationRepository.save(newConversation);
		
		//// create paticipants
//		String convId = newConversation.getId(); //null or not null ??
		Participant p1 = Participant.builder()
							.conversation(newConversation)
							.userAccount(user1Account)
							.timeJoined(LocalDateTime.now())
							.timeLeft(null)
							.build();
		Participant p2 = Participant.builder()
				.conversation(newConversation)
				.userAccount(user2Account)
				.timeJoined(LocalDateTime.now())
				.timeLeft(null)
				.build();
		participantRepository.save(p1);
		participantRepository.save(p2);
		
		// create matching relationship
		Matching matching = Matching.builder()
				.partnerAccount(user2Account)
				.relationshipType(RelationshipType.MATCH.name())
				.userAccount(user1Account)
						.build();
		matchingRepository.save(matching);
		return matching;
	}
	
	// final: helper function
}
