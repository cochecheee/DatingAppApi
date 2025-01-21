package com.datingapp.service;

import java.util.Optional;

import com.datingapp.entity.UserAccount;

public interface UserAccountService {

	Optional<UserAccount> findByEmail(String email);

}
