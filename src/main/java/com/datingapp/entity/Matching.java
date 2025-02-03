package com.datingapp.entity;

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
@Table(name = "matching")
public class Matching {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	// partner if
	@ManyToOne
    @JoinColumn(name = "partner_account_id")
	private UserAccount partnerAccount;
	
	// userid
	@ManyToOne
    @JoinColumn(name = "user_account_id")
	private UserAccount userAccount;
	
	// relationship type
	private String relationshipType;
}
