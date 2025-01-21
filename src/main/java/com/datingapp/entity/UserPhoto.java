package com.datingapp.entity;

import java.time.LocalDateTime;

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
@Table(name = "user_photo")
public class UserPhoto {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String link;
	private String details;
	
	private LocalDateTime timeAdded;
	
	private Boolean active;
	
	// user
	@ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;
}
