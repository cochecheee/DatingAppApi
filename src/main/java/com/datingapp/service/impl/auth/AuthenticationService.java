package com.datingapp.service.impl.auth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datingapp.configs.jwt.JwtService;
import com.datingapp.configs.security.UserUserDetails;
import com.datingapp.dto.ErrorResponse;
import com.datingapp.dto.auth.AuthenticationRequest;
import com.datingapp.dto.auth.AuthenticationResponse;
import com.datingapp.dto.request.RegisterRequest;
import com.datingapp.dto.request.ResetPasswordRequest;
import com.datingapp.dto.response.RegisterResponse;
import com.datingapp.dto.response.ResetPasswordResponse;
import com.datingapp.entity.Gender;
import com.datingapp.entity.UserAccount;
import com.datingapp.exception.ErrorMessage;
import com.datingapp.repository.GenderRepository;
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
	@Autowired
	private GenderRepository genderRepository;
//	@Value("${spring.security.oauth2.client.registration.google.client-id}")
//	private String YOUR_GOOGLE_CLIENT_ID;

	/* ONLY HAVE 2 ROLES: USER AND GUEST */

	// login
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = accountRepository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(new UserUserDetails(user));
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	// request register
	public Object requestRegister(RegisterRequest req) {
		// if email exist in database
		Optional<UserAccount> user = accountRepository.findByEmail(req.getEmail());
		if(user.isPresent() ) {
			return ErrorResponse.builder()
					.message(ErrorMessage.EMAIL_EXIST)
					.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
					.status(409)
					.build();
		}
		
		String otp = otpSenderService.generateOTP(req.getEmail());
		otpSenderService.sendOTPEmail(req.getEmail(), otp);
		
		RegisterResponse registerRes = RegisterResponse.builder()
				.email(req.getEmail())
				.password(req.getPassword())
				.email(req.getEmail())
				.firstname(req.getFirstname())
				.lastname(req.getLastname())
				.otpCode(otp)
				.gender(req.getGender())
				.nickname(req.getNickname())
				.build();
		return registerRes;
	}

	// register
	// register -> send otp through email -> check otp -> failed/success
	public Object register(RegisterResponse response) {
		// check existed mail
		Optional<UserAccount> userExisted = accountRepository.findByEmail(response.getEmail());
		if(userExisted.isEmpty()) {
			return ErrorResponse.builder()
					.message(ErrorMessage.EMAIL_INVALID)
					.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
					.status(409)
					.build();
		}
		
		// check valid otp
		if (!otpSenderService.validateOTP(response.getEmail(), response.getOtpCode())) {
			//System.out.println("false otp");
			//TODO 3: add logic for this situation
			return ErrorResponse.builder()
					.status(409)
					.message(ErrorMessage.OTP_INVALID)
					.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
					.build();
		}
		
		Optional<Gender> gender = genderRepository.findById(response.getGender().toString());
		if(gender.isEmpty()) {
			// add logic later
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid gender");
		}
		var user = UserAccount.builder()
				.password(encoder.encode(response.getPassword()))
				.email(response.getEmail())
				.firstName(response.getFirstname())
				.lastName(response.getLastname())
				.nickname(response.getNickname())
				.gender(gender.get())
				.build();
		
		accountRepository.save(user);
		otpSenderService.removeOTP(response.getEmail());
		var jwtToken = jwtService.generateToken(new UserUserDetails(user));
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
	
	// google sign in
//	public AuthenticationResponse googleSignIn(GoogleSignInRequest signInRequest) {
//		try {
//	        // Verify ID Token with Google
//	        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
//	                .setAudience(Collections.singletonList("YOUR_GOOGLE_CLIENT_ID")) 
//	                .build();
//
//	        GoogleIdToken token = null;
//			try {
//				token = verifier.verify(signInRequest.getIdToken());
//			} catch (java.io.IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        if (token == null) {
//	            return AuthenticationResponse.builder()
//	                    .token("Invalid ID token")
//	                    .build();  // Return an error message if token is invalid
//	        }
//
//	        // If valid, store the user in the database
//	        Optional<UserAccount> existingUser = accountRepository.findByEmail(signInRequest.getEmail());
//	        UserAccount newUser = new UserAccount();
//	        if (!existingUser.isPresent()) {
//	            // If user does not exist, register the user
//	   
//	            newUser.setEmail(signInRequest.getEmail());
//	            newUser.setPassword(encoder.encode("123456"));
//	            accountRepository.save(newUser);
//	        }
//
//	        // Generate JWT token
//	        var jwtToken = jwtService.generateToken(new UserUserDetails(newUser));
//
//	        // Return the JWT token in the AuthenticationResponse
//	        return AuthenticationResponse.builder()
//	                .token(jwtToken)
//	                .build();
//
//	    } catch (GeneralSecurityException | IOException e) {
//	        // Handle exceptions during token verification
//	        return AuthenticationResponse.builder()
//	                .token("Error verifying token")
//	                .build();
//	    }
//	}

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
