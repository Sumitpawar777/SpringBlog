package com.javabrains.springblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.javabrains.springblog.dto.LoginRequest;
import com.javabrains.springblog.dto.RegisterRequest;
import com.javabrains.springblog.service.AuthService;
import com.javabrains.springblog.service.AuthenticationResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	 @Autowired
	 private AuthService authService;
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/signup")
	 public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
	        authService.signup(registerRequest);
	        return new ResponseEntity<>(HttpStatus.OK);
	 }
	
	  @PostMapping("/login")
	  public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
	        return authService.login(loginRequest);
	  }

}
