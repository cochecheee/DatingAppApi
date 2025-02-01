package com.datingapp.dto.chat;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
	private String convID;
	private String userSendID;
	private String userReceivID;
	private String type; // sending text or image
	private String messageText;
	private LocalDateTime timestamp;
	private boolean isSeen;
	private String userImageUrl;
}
