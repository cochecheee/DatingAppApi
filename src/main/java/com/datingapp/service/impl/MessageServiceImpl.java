package com.datingapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datingapp.dto.chat.MessageResponse;
import com.datingapp.entity.Message;
import com.datingapp.repository.IMessageRepository;
import com.datingapp.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private IMessageRepository messageRepository;
	
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
										.userID(mess.getParticipant().getUserAccount().getId())
										.userImageUrl("")
										.type("text") //message
										.timestamp(mess.getTimestamp())
										.build();
			messagesResponse.add(res);
		}
		return messagesResponse;
	}

}
