package com.datingapp.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.datingapp.dto.chat.MessageRequest;
import com.datingapp.dto.chat.MessageResponse;
import com.datingapp.entity.Conversation;
import com.datingapp.entity.Message;
import com.datingapp.entity.Participant;
import com.datingapp.enums.MessageType;
import com.datingapp.repository.IConversationRepository;
import com.datingapp.repository.IMessageRepository;
import com.datingapp.repository.IParticipantRepository;
import com.datingapp.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private IMessageRepository messageRepository;
	@Autowired
	private IConversationRepository convRepository;
	@Autowired
	private IParticipantRepository participantRepository;
	
	@Override
	public List<?> getMessagesOfTwoUsers(String user1ID, String user2ID) {
		// all message
		List<Message> messages = messageRepository.findAllMessagesBetweenUsers(user1ID, user2ID);
		
		List<MessageResponse> messagesResponse = new ArrayList<>();
		
		for(Message mess : messages) {
			//get user photo later
			MessageResponse res = MessageResponse.builder()
										.isSeen(false)
										.messageText(mess.getMessageText())
										.userSendID(user1ID)
										.userReceivID(user2ID)
										.userImageUrl("")
										.type(MessageType.TEXT.toLowerCaseName()) //message
										.timestamp(mess.getTimestamp())
										.build();
			messagesResponse.add(res);
		}
		return messagesResponse;
	}
	
	@Override
	@Transactional
	public Object sendingMessageToUser(String user1, String user2, MessageRequest req) {
		// find conversation
		Optional<Conversation> conversation = convRepository.findById(req.getConvID());
		if(conversation.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No conversation bruh!");
		}
		
		// find participant who send the message
		Optional<Participant> sender = participantRepository.findParticipantByUserIdAndConversationId(user1, req.getConvID());
		if(sender.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found this participant");
		}
		
		// save the message
		Message newMessage = Message.builder()
									.messageText(req.getMessageText())
									.messageType(req.getType())
									.participant(sender.get())
									.timestamp(LocalDateTime.now())
									.build();
		messageRepository.save(newMessage);
		
		return MessageResponse.builder()
								.convID(req.getConvID())
								.messageText(req.getMessageText())
								.timestamp(newMessage.getTimestamp())
								.isSeen(false)
								.userImageUrl("")
								.userReceivID(user2)
								.userSendID(user1)
								.type(req.getType())
								.build();
	}

}
