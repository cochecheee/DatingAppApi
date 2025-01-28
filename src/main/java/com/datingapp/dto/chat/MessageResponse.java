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
	private String userID;
	private String type; // sending text or image
	private String messageText;
	private LocalDateTime timestamp;
	private boolean isSeen;
	private String userImageUrl;
}
