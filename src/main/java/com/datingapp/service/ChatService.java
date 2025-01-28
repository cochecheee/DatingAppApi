package com.datingapp.service;

import java.util.List;

public interface ChatService {

	Object createChat(String user2ID);

	List<?> getAllChatsOfUser();

}
