package com.datingapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datingapp.entity.Message;

@Repository
public interface IMessageRepository extends JpaRepository<Message, String> {
	// find the lastest message between 2 user
	@Query("SELECT m FROM Message m " + "WHERE m.participant.conversation.id IN (" + " SELECT c.id FROM Conversation c "
			+ "JOIN Participant p1 ON c.id = p1.conversation.id " + " JOIN Participant p2 ON c.id = p2.conversation.id "
			+ "   WHERE p1.userAccount.id = :user1Id " + " AND p2.userAccount.id = :user2Id" + ") "
			+ "ORDER BY m.timestamp DESC " + "LIMIT 1")
	Optional<Message> findLatestMessageBetweenUsers(@Param("user1Id") String user1Id, @Param("user2Id") String user2Id);

	// find all message between 2 user
	@Query("SELECT m FROM Message m " + "WHERE m.participant.conversation.id IN ("
			+ "   SELECT c.id FROM Conversation c " + "   JOIN Participant p1 ON c.id = p1.conversation.id "
			+ "   JOIN Participant p2 ON c.id = p2.conversation.id " + "   WHERE p1.userAccount.id = :user1ID "
			+ "   AND p2.userAccount.id = :user2ID" + ") " + "ORDER BY m.timestamp ASC")
	List<Message> findAllMessagesBetweenUsers(@Param("user1ID") String user1ID, @Param("user2ID") String user2ID);

}
