package com.datingapp.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
	// private String userID;
	
	private String firstname;
	private String lastname;
	private String email;
	private String nickname;
	private Integer gender;
	private String password;
	private String otpCode;
}
