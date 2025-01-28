package com.datingapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datingapp.entity.Participant;
import com.datingapp.entity.UserAccount;

@Repository
public interface IParticipantRepository extends JpaRepository<Participant, String> {
	// find participant by userid and conversationid
	@Query("SELECT p FROM Participant p " + "WHERE p.userAccount.id = :userID AND p.conversation.id = :conversationID")
	Optional<Participant> findParticipantByUserIdAndConversationId(@Param("userID") String userID,
			@Param("conversationID") String conversationID);

	// find participant by conversationID
	List<Participant> findPaticipantsByConversationId(String conversationID);

	// find user by participant id
	@Query("SELECT p.userAccount FROM Participant p WHERE p.id = :participantId")
	Optional<UserAccount> findUserByParticipantId(@Param("participantId") String participantId);
	
	// find participant having conversation equals conversation id and user_acc_id differs from userID
	@Query("SELECT p FROM Participant p " +
	           "WHERE p.conversation.id = :conversationId " +
	           "AND p.userAccount.id <> :userId")
    Optional<Participant> findOtherParticipantInConversation(
            @Param("conversationId") String conversationId,
            @Param("userId") String userId);

}
