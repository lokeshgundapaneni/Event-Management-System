package com.eventhub.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.request.LoginRequest;
import com.eventhub.dto.request.RegisterRequest;
import com.eventhub.dto.response.AuthResponse;
import com.eventhub.dto.response.UserResponse;
import com.eventhub.entity.User;
import com.eventhub.repository.UserRepository;
import com.eventhub.security.jwt.JwtUtil;
import com.eventhub.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	public AuthController(AuthenticationManager authenticationManager,JwtUtil jwtUtil,UserRepository userRepository,UserService userService)
	{
		this.userService=userService;
		this.userRepository=userRepository;
		this.authenticationManager=authenticationManager;
		this.jwtUtil=jwtUtil;
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest request)
	{
		 authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
		 
		 User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
		 
		 String token=jwtUtil.generateToken(request.getEmail(),user.getRole().name());
		 return new AuthResponse(token);
	}
	
	@PostMapping("/register")
	public UserResponse register(
	        @Valid @RequestBody RegisterRequest request)
	{
	    return userService.createUser(request);
	}
	
	

}
