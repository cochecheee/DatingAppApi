package com.datingapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String firstname;
	private String lastname;
	private String nickname;
	private String email;
	private Integer gender;
	private String password;
}
