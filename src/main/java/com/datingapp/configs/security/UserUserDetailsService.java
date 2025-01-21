package com.datingapp.configs.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.datingapp.entity.UserAccount;
import com.datingapp.repository.IUserAccountRepository;

@Service
public class UserUserDetailsService implements UserDetailsService {
	@Autowired
	private IUserAccountRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserAccount> acc = userRepository.findByEmail(email);
		if (acc.isPresent()) {
			System.out.println("User found: " + acc.get().getEmail());
			return new UserUserDetails(acc.get()); // tạo user security cho user này
		}
		// System.out.println("User not found: " + username);
		throw new UsernameNotFoundException(email);
	}
}
