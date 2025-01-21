package com.datingapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datingapp.entity.OTP;
import com.datingapp.repository.IOTPRepository;
import com.datingapp.service.OTPService;

@Service
public class OTPServiceImpl implements OTPService {
	@Autowired 
	private IOTPRepository otpRepository;

	@Override
	public <S extends OTP> S save(S entity) {
		return otpRepository.save(entity);
	}

	@Override
	public void deleteByOtp(String otp) {
		otpRepository.deleteByOtp(otp);
	}
}
