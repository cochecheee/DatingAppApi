package com.datingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordResponse {
	private String email;
	private String otp;
	private String newPassword;
	private String confirmedPassword;
}
