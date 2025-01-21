package com.datingapp.configs.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.datingapp.entity.UserAccount;
import com.datingapp.enums.RoleName;

import lombok.Builder;

@Builder
public class UserUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// initialize account for security
	private UserAccount account;

	public UserUserDetails(UserAccount account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(RoleName.USER.name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return account.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // or add your logic
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // or add your logic
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // or add your logic
	}

	@Override
	public boolean isEnabled() {
		return true; // or add your logic
	}
}
