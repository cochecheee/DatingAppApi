package com.datingapp.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
	
	private String email;
	private String firstName;
	private String lastName;
	// bio
	private String details;
	private String nickname;
	private BigDecimal polarity;

	private String gender;
}
