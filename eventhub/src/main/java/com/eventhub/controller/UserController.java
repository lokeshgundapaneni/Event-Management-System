package com.eventhub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.request.RegisterRequest;
import com.eventhub.entity.User;
import com.eventhub.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService service)
	{
		this.userService=service;
	}
	
	@PostMapping 
	public User createUser(@Valid @RequestBody RegisterRequest request)
	{
		return userService.createUser(request);
	}
	
	@GetMapping
	public List<User> getAllUsers()
	{
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id)
	{
		return userService.getUserById(id);
	}
	
	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id,@Valid @RequestBody RegisterRequest request)
	{
		return userService.updateUser(id, request);
	}
	
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id)
	{
		userService.deleteUser(id);
		return "User deleted successfully";
	}

}
