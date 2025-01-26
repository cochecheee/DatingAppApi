package com.datingapp.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datingapp.dto.ErrorResponse;
import com.datingapp.dto.response.ChatResponse;
import com.datingapp.entity.Conversation;
import com.datingapp.entity.Participant;
import com.datingapp.entity.UserAccount;
import com.datingapp.exception.ErrorMessage;
import com.datingapp.repository.IConversationRepository;
import com.datingapp.repository.IParticipantRepository;
import com.datingapp.repository.IUserAccountRepository;
import com.datingapp.service.ConversationService;

@Service
public class ConversationServiceImpl implements ConversationService {
	@Autowired
	private IConversationRepository conversationRepository;
	@Autowired
	private IUserAccountRepository userRepository;
	@Autowired
	private IParticipantRepository participantRepository;
	
	@Override
	@Transactional
	public Object createChat(String user2ID) {
		// get user 1 user name
		String user1Email = SecurityContextHolder.getContext().getAuthentication().getName();
		//get user1
		UserAccount user1 = userRepository.findByEmail(user1Email).get();
		
		//get user2
		Optional<UserAccount> user2 = userRepository.findById(user2ID);
		//check valid id
		if(user2.isEmpty()) {
			return ErrorResponse.builder()
					.message(ErrorMessage.USER_NOT_EXIST)
					.status(404)
					.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
					.build();
		}
		// create conversation by user 1
		Conversation conv = Conversation.builder()
//									.id(uuid)
									.userAccount(user1)
									.timeStarted(LocalDateTime.now())
									.build();
		//save
		conversationRepository.save(conv);
		
		// create paticipants for this conversation
		Participant p1 = Participant.builder()
									.conversation(conv)
									.timeJoined(LocalDateTime.now())
									.userAccount(user1)
									.build();
		Participant p2 = Participant.builder()
									.conversation(conv)
									.timeJoined(LocalDateTime.now())
									.userAccount(user2.get())
									.build();
		// save
		participantRepository.save(p1);
		participantRepository.save(p2);
		List<String> participantIds = List.of(p1.getId(), p2.getId());
		String convID = conv.getId();
		
		return ChatResponse.builder()
						.conversationID(convID)
						.participantsID(participantIds)
						.build();
	}
	/*-------------------------------------------------------------------------------------------------*/
}
