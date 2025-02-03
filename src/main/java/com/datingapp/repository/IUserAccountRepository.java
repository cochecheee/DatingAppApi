package com.datingapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datingapp.entity.UserAccount;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount, String> {
	// find user by email
	Optional<UserAccount> findByEmail(String email);

	// find user by nickname
	Optional<UserAccount> findByNickname(String nickname);

	// find all user that having id differ from all partner_id of this user
	@Query("SELECT u FROM UserAccount u WHERE u.id != :userId "
			+ "AND u.id NOT IN (SELECT m.partnerAccount.id FROM Matching m WHERE m.userAccount.id = :userId)")
	List<UserAccount> findAllUsersNotMatchedWith(@Param("userId") String userId);
}
