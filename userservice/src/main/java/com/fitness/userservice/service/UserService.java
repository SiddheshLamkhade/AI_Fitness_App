package com.fitness.userservice.service;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public UserResponse register(RegisterRequest request) {
		
		if(repo.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exist");
		}
		
		User user=new User();
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		
		User savedUser = repo.save(user);
		UserResponse userResponse= new UserResponse();
		userResponse.setId(savedUser.getId());
		userResponse.setPassword(savedUser.getPassword());
		userResponse.setEmail(savedUser.getEmail());
		userResponse.setFirstName(savedUser.getFirstName());
		userResponse.setLastName(savedUser.getLastName());
		userResponse.setCreatedAt(savedUser.getCreatedAt());
		userResponse.setUpdatedAt(savedUser.getUpdatedAt());
		return userResponse;
	}

	public UserResponse getUserProfile(String userId) {
		User user = repo.findById(userId).orElseThrow(()-> new RuntimeException("User Not Found"));
		UserResponse userResponse= new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setPassword(user.getPassword());
		userResponse.setEmail(user.getEmail());
		userResponse.setFirstName(user.getFirstName());
		userResponse.setLastName(user.getLastName());
		userResponse.setCreatedAt(user.getCreatedAt());
		userResponse.setUpdatedAt(user.getUpdatedAt());
		return userResponse;
		
	}

	public Boolean existByUserId(String userId) {
		log.info("Calling User Validation API",userId);
		return repo.existsById(userId);
	}
	
	
}
