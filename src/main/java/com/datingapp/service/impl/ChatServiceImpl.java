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
import com.datingapp.dto.chat.ChatResponse;
import com.datingapp.dto.chat.ListChatResponse;
import com.datingapp.entity.Conversation;
import com.datingapp.entity.Message;
import com.datingapp.entity.Participant;
import com.datingapp.entity.UserAccount;
import com.datingapp.exception.ErrorMessage;
import com.datingapp.repository.IConversationRepository;
import com.datingapp.repository.IMessageRepository;
import com.datingapp.repository.IParticipantRepository;
import com.datingapp.repository.IUserAccountRepository;
import com.datingapp.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	private IConversationRepository conversationRepository;
	@Autowired
	private IUserAccountRepository userRepository;
	@Autowired
	private IParticipantRepository participantRepository;
	@Autowired
	private IMessageRepository messageRepository;
	
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
		
		// chat response
		List<String> participantIds = List.of(p1.getId(), p2.getId());
		String convID = conv.getId();
		
		return ChatResponse.builder()
						.conversationID(convID)
						.participantsID(participantIds)
						.build();
	}
	
	@Override
	public List<?> getAllChatsOfUser() {
		// TODO 1: get username of current user
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserAccount user = userRepository.findByEmail(username).get();
		String userID = user.getId();
		
		// TODO 2: find conversation of this user
		List<Conversation> conversations = conversationRepository.findConversationsByUserID(userID);
		// TODO 3: return list of this conversation
		List<ListChatResponse> allChats = new ArrayList<>();
		/*
		 * 	private String conversationID;
			private String partnerID;
			private String partnerName;
			// private String partnerImage;
			private String latestMessage;
			private int partnerStatus;
		 * */
		for(Conversation chat : conversations) {
			Participant p = participantRepository.findOtherParticipantInConversation(chat.getId(), userID).get(); // no group
			Message m = messageRepository.findLatestMessageBetweenUsers(userID, p.getUserAccount().getId()).get();
			ListChatResponse chatRes = ListChatResponse.builder()
									.conversationID(chat.getId())
									.partnerID(p.getId())
									.partnerName(p.getUserAccount().getFirstName() + " " + p.getUserAccount().getLastName())
									.latestMessage(m.getMessageText())
									.partnerStatus(0)
									.build();
			allChats.add(chatRes);
		}
		return allChats;
	}
	/*-------------------------------------------------------------------------------------------------*/
	// helper function
	// TODO final step: divide main feature to smaller function
}
