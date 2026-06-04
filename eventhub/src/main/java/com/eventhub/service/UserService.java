package com.eventhub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.dto.request.RegisterRequest;
import com.eventhub.entity.User;
import com.eventhub.enums.Role;
import com.eventhub.exception.UserNotFoundException;
import com.eventhub.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository repo;
	
	public UserService(UserRepository repo)
	{
		this.repo=repo;
	}
	
//	create the user
	public User createUser(RegisterRequest request)
	{
		User user=new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setRole(Role.USER);
		return repo.save(user);
	}
	
//	get all the users 
	public List<User> getAllUsers()
	{
		return repo.findAll();
	}
	
//	get user by id
	public User getUserById(Long id)
	{
		return repo.findById(id).orElseThrow(()->
									new UserNotFoundException("User not found with id : "+id));
	}
	
//	update User By Id
	public User updateUser(Long id,RegisterRequest request)
	{
		User user = repo.findById(id).orElseThrow(()->
									new UserNotFoundException("User not found with id : "+id));
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		return repo.save(user);
	}
	
//	delete the user
	public void deleteUser(Long id)
	{
		User user = repo.findById(id).orElseThrow(()->
									new UserNotFoundException("User not found with id : "+id));
		repo.delete(user);
	}
}
