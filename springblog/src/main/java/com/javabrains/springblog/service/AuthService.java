package com.javabrains.springblog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.javabrains.springblog.dto.LoginRequest;
import com.javabrains.springblog.dto.RegisterRequest;
import com.javabrains.springblog.model.User;
import com.javabrains.springblog.repository.UserRepository;
import com.javabrains.springblog.security.JwtProvider;


@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUserName(registerRequest.getUsername());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		userRepository.save(user);
	}


	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}


	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
		String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
	}


	public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

}
