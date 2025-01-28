package com.datingapp.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListChatResponse {
	private String conversationID;
	private String partnerID;
	private String partnerName;
	// private String partnerImage;
	private String latestMessage;
	private int partnerStatus;

}
