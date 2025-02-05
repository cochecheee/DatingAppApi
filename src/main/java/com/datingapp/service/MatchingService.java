package com.datingapp.service;

import java.util.List;

public interface MatchingService {

	List<?> getCardForUser();

	Object matching(String user1, String user2);

}
