package com.datingapp.service;

import com.datingapp.entity.OTP;

public interface OTPService {

	<S extends OTP> S save(S entity);

	void deleteByOtp(String otp);
	
}	
