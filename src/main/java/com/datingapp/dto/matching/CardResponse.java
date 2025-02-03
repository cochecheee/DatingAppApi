package com.datingapp.dto.matching;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {
	private String user_id;
	private String user_image;
	private String user_bio;
	private String user_gender;
	private String user_name;

}
