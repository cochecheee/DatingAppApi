package com.datingapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class InterestedInGender {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	// user
	@ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;
	
	// gender
	@ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;
}	
