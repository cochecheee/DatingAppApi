package com.datingapp.dto.chat;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
	private String conversationID; //conversation id
	//private List<UserAccount> users;
	private List<String> participantsID;
}
