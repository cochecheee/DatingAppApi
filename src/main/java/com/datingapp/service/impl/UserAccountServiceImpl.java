package com.datingapp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datingapp.entity.UserAccount;
import com.datingapp.repository.IUserAccountRepository;
import com.datingapp.service.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService{

	@Autowired
	private IUserAccountRepository userRepository;

	@Override
	public Optional<UserAccount> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
