package com.datingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datingapp.entity.UserAccount;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount, String>{
	//find user by email
	Optional<UserAccount> findByEmail(String email);
}
