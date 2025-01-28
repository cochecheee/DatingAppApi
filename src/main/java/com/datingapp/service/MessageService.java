package com.datingapp.service;

import java.util.List;

public interface MessageService {

	List<?> getMessagesOfTwoUsers(String user1ID, String user2ID);

}
