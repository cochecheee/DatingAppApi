package com.datingapp.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.datingapp.configs.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	UserDetailsService userDetailsService() {
		return new UserUserDetailsService();
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthFilter() {
		return new JwtAuthenticationFilter();
	}

	// AuthenticationManager sử dụng AuthenticationProvider để xác minh thông tin
	// đăng nhập người dùng.
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	// filter chain
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()) // Tắt CSRF (chỉ để demo, không khuyến nghị production)
				.cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/v1/auth/**").permitAll()
//	                		.requestMatchers("/api/v1/users/*").hasRole("ADMIN")
						.anyRequest().authenticated())
//						.anyRequest().permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider()) // Cung cấp AuthenticationProvider
	            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class) // Thêm bộ lọc JWT
				.build();
	}
}
