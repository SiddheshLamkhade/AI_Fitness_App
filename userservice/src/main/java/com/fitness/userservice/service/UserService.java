package com.fitness.userservice.service;
import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
@Service
public class UserService{
	public UserResponse register(RegisterRequest request) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		return null;
	}
	public UserResponse getUserProfile(String userId) {
		return null;
	}
}
