package com.datingapp.controller.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datingapp.dto.auth.AuthenticationRequest;
import com.datingapp.dto.auth.AuthenticationResponse;
import com.datingapp.dto.request.RegisterRequest;
import com.datingapp.dto.request.ResetPasswordRequest;
import com.datingapp.dto.response.RegisterResponse;
import com.datingapp.dto.response.ResetPasswordResponse;
import com.datingapp.service.impl.auth.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	@Autowired
	private AuthenticationService authService;
	
	//login
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}
	
	// TODO 1: error saving gender
	// request register
	@PostMapping("/request-register")
	public ResponseEntity<?> requestRegister(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.requestRegister(request));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(
			@RequestBody RegisterResponse response) {
		return ResponseEntity.ok(authService.register(response));
	}
	// end TODO
	
	// request reset password
	@PostMapping("/request-reset-password")
	public ResponseEntity<ResetPasswordResponse> requestResetPassword(@RequestBody ResetPasswordRequest request) {
		return ResponseEntity.ok(authService.requestResetPassword(request));
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<Map<String,String>> resetPassword(@RequestBody ResetPasswordResponse response) {
		return ResponseEntity.ok(authService.resetPassword(response));
	}
	
	// google login
//	@PostMapping("/google-login")
//	public ResponseEntity<AuthenticationResponse> googleLogin(
//			@RequestBody GoogleSignInRequest request) {
//		return ResponseEntity.ok(authService.googleSignIn(request));
//	}
}
