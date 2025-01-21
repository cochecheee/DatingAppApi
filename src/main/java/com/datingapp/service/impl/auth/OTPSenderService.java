package com.datingapp.service.impl.auth;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.datingapp.entity.OTP;
import com.datingapp.repository.IOTPRepository;

@Service
public class OTPSenderService {
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private IOTPRepository otpRepository;
	
	// store OTPs temporarily (use a database in production) --> update later
    //private final Map<String, String> otpStorage = new HashMap<>();
	//private OTP otpStorage;
    
    // generate OTP
    public String generateOTP(String email) {
        String otp = String.valueOf(new Random().nextInt(999999 - 100000) + 100000); // 6-digit OTP
        //store otp
        OTP otpObj = new OTP();
        otpObj.setEmail(email);
        otpObj.setOtp(otp);
        
        // store otp object
        	// delete older otp
        Optional<OTP> olderOTP = otpRepository.findByEmail(email);
        if(olderOTP.isPresent()) {
        	otpRepository.delete(olderOTP.get());
        }
        
        otpRepository.save(otpObj);
        
        return otp;
    }
    
    // validate OTP
    public boolean validateOTP(String email, String otp) {
    	// find OTP by email
    	Optional<OTP> otpCode = otpRepository.findByEmail(email);
    	
        return otp.equals(otpCode.get().getOtp());
    }

    // send OTP via email
    public void sendOTPEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        message.setFrom("pythonsendmail8@gmail.com");

        mailSender.send(message);
    }

    // remove OTP after validation
    public void removeOTP(String email) {
    	Optional<OTP> removeOTP = otpRepository.findByEmail(email);
        
    	otpRepository.delete(removeOTP.get());
    }
}
