package com.datingapp.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
	private String email;
	private String firstName;
	private String lastName;
	// bio
	private String details;
	private String nickname;

	private String gender;
}
