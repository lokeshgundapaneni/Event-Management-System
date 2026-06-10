package com.eventhub.service;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eventhub.dto.request.RegisterRequest;
import com.eventhub.dto.response.UserResponse;
import com.eventhub.entity.User;
import com.eventhub.exception.UserNotFoundException;
import com.eventhub.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
    
    public UserResponse createUser(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        User savedUser = repo.save(user);
        return mapToResponse(savedUser);
    }
    
    public List<UserResponse> getAllUsers() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    public UserResponse getUserById(Long id) {
        User user = repo.findById(id).orElseThrow(() ->
                                    new UserNotFoundException("User not found with id : " + id));
        return mapToResponse(user);
    }
    
    public UserResponse updateUser(Long id, RegisterRequest request) {
        User user = repo.findById(id).orElseThrow(() ->
                                    new UserNotFoundException("User not found with id : " + id));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        User updatedUser = repo.save(user);
        return mapToResponse(updatedUser);
    }
    
    public void deleteUser(Long id) {
        User user = repo.findById(id).orElseThrow(() ->
                                    new UserNotFoundException("User not found with id : " + id));
        repo.delete(user);
    }
}