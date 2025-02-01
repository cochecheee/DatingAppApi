package com.datingapp.service;

import java.util.List;

import com.datingapp.dto.chat.MessageRequest;

public interface MessageService {

	List<?> getMessagesOfTwoUsers(String user1ID, String user2ID);

	Object sendingMessageToUser(String user1, String user2, MessageRequest req);

}
