package com.datingapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(name = "message_text")
    private String messageText;
	
	private String messageType;

    @Column(name = "ts")
    private LocalDateTime timestamp;
    
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;
}	
