package com.datingapp.entity;

import java.math.BigDecimal;

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
@Entity
@Builder
@Table(name = "users")
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String firstName;
	private String lastName;
	
	// bio
	private String details;
	private String nickname;
	private BigDecimal polarity;
	
	//gender_id related to gender table
	@ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;
}
