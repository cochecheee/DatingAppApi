package com.datingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datingapp.entity.Conversation;

@Repository
public interface IConversationRepository extends JpaRepository<Conversation, String> {
	// find conversation based on user1ID and user2ID (ok)
	@Query("SELECT c FROM Conversation c " + "WHERE c.id IN (" + "   SELECT p1.conversation.id FROM Participant p1 "
			+ "   JOIN Participant p2 ON p1.conversation.id = p2.conversation.id "
			+ "   WHERE p1.userAccount.id = :user1ID AND p2.userAccount.id = :user2ID" + ")")
	Conversation findConversationByUser1And2ID(@Param("user1ID") String user1ID, @Param("user2ID") String user2ID);
}
