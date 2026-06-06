package com.eventhub.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventhub.dto.request.RegisterRequest;
import com.eventhub.dto.response.UserResponse;
import com.eventhub.entity.User;
import com.eventhub.enums.Role;
import com.eventhub.exception.UserNotFoundException;
import com.eventhub.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository repo;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository repo,PasswordEncoder passwordEncoder)
	{
		this.repo=repo;
		this.passwordEncoder=passwordEncoder;
	}
	
	public UserResponse mapToResponse(User user)
	{
		return new UserResponse(user.getId(),user.getName(),user.getEmail(),user.getRole());
	}
	
//	create the user
	public UserResponse createUser(RegisterRequest request)
	{
		User user=new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		User savedUser=repo.save(user);
		return mapToResponse(savedUser);
	}
	
//	get all the users 
	public List<UserResponse> getAllUsers()
	{
		return repo.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}
	
//	get user by id
	public UserResponse getUserById(Long id)
	{
		User user=repo.findById(id).orElseThrow(()->
									new UserNotFoundException("User not found with id : "+id));
		return mapToResponse(user);
	}
	
//	update User By Id
	public UserResponse updateUser(Long id,RegisterRequest request)
	{
		User user = repo.findById(id).orElseThrow(()->
									new UserNotFoundException("User not found with id : "+id));
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		User updatedUser=repo.save(user);
		return mapToResponse(updatedUser);
	}
	
//	delete the user
	public void deleteUser(Long id)
	{
		User user = repo.findById(id).orElseThrow(()->
									new UserNotFoundException("User not found with id : "+id));
		repo.delete(user);
	}
}
