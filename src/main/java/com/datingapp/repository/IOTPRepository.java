package com.datingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datingapp.entity.OTP;

@Repository
public interface IOTPRepository extends JpaRepository<OTP, Integer> {
	// delete by otpCode value
	void deleteByOtp(String otp);
	// find OTP by email
	Optional<OTP> findByEmail(String email);
}
