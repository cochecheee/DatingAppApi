package com.datingapp.dto.register;

import lombok.Data;

@Data
public class ResetPasswordRequest {
	private String email;
	private String newPass;
	private String confirmedPass;
}
