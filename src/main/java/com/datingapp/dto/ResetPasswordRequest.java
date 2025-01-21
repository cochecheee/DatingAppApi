package com.datingapp.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
	private String email;
	private String newPass;
	private String confirmedPass;
}
