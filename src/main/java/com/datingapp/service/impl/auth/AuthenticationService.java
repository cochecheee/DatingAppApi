package com.datingapp.service.impl.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.datingapp.configs.jwt.JwtService;
import com.datingapp.configs.security.UserUserDetails;
import com.datingapp.dto.RegisterRequest;
import com.datingapp.dto.RegisterResponse;
import com.datingapp.dto.ResetPasswordRequest;
import com.datingapp.dto.ResetPasswordResponse;
import com.datingapp.dto.auth.AuthenticationRequest;
import com.datingapp.dto.auth.AuthenticationResponse;
import com.datingapp.entity.UserAccount;
import com.datingapp.repository.IUserAccountRepository;

@Service
public class AuthenticationService {
	@Autowired
	private IUserAccountRepository accountRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private OTPSenderService otpSenderService;

	/* ONLY HAVE 2 ROLES: USER AND GUEST */

	// login
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		// TODO Auto-generated method stub
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		var user = accountRepository.findByEmail(request.getEmail()).orElseThrow();

		var jwtToken = jwtService.generateToken(new UserUserDetails(user));

		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	// request register
	public RegisterResponse requestRegister(RegisterRequest req) {
		String otp = otpSenderService.generateOTP(req.getEmail());
		otpSenderService.sendOTPEmail(req.getEmail(), otp);

		return RegisterResponse.builder().email(req.getEmail()).password(req.getPassword()).email(req.getEmail())
				.firstname(req.getFirstname()).lastname(req.getLastname()).otpCode(otp).build();
	}

	// register
	// register -> send otp through email -> check otp -> failed/success
	public AuthenticationResponse register(RegisterResponse response) {
		// TODO Auto-generated method stub

		// check valid otp
		// Validate OTP and password matching
		if (!otpSenderService.validateOTP(response.getEmail(), response.getOtpCode())) {

			System.out.println("false otp");
			// add logic for this situation
		}

		var user = UserAccount.builder().password(encoder.encode(response.getPassword())).email(response.getEmail())
				.firstName(response.getFirstname()).lastName(response.getLastname()).build();

		accountRepository.save(user);

		// remove otp
		otpSenderService.removeOTP(response.getEmail());

		var jwtToken = jwtService.generateToken(new UserUserDetails(user));
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	// request reset password
	public ResetPasswordResponse requestResetPassword(ResetPasswordRequest req) {
		String otp = otpSenderService.generateOTP(req.getEmail());
		otpSenderService.sendOTPEmail(req.getEmail(), otp);

		return ResetPasswordResponse.builder().email(req.getEmail()).newPassword(req.getNewPass())
				.confirmedPassword(req.getConfirmedPass()).otp(otp).build();
	}

	// reset password
	public Map<String, String> resetPassword(ResetPasswordResponse res) {

		Map<String, String> response = new HashMap<>();
		// fetch user by email
		var userOptional = accountRepository.findByEmail(res.getEmail());
		if (userOptional.isEmpty()) {
			response.put("error", "User not found with the provided email.");
			return response;
		}

		UserAccount user = userOptional.get();

		// validate OTP and password matching
		if (!otpSenderService.validateOTP(res.getEmail(), res.getOtp())) {
			response.put("error", "Invalid OTP.");
			return response;
		}

		if (!res.getConfirmedPassword().equals(res.getNewPassword())) {
			response.put("error", "Passwords do not match.");
			return response;
		}

		// reset password
		user.setPassword(encoder.encode(res.getNewPassword()));
		accountRepository.save(user);
		
		// delete otp
		otpSenderService.removeOTP(res.getEmail());

		response.put("message", "Password reset successfully.");
		return response;
	}
}
