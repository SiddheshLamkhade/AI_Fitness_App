package com.fitness.activityservice.service;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
//@RequiredArgsConstructor
@Slf4j
public class ActivityService {
	
	private final ActivityRepository repo;
	private final UserValidationService userValidationService;
	private final RabbitTemplate rabbitTemplate;
	
	@Autowired  // this is needed to inject the repo
	public ActivityService(ActivityRepository repo,UserValidationService userValidationService,RabbitTemplate rabbitTemplate) {
	        this.repo = repo;
			this.userValidationService = userValidationService;
			this.rabbitTemplate =rabbitTemplate;
	}

	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	
	public ActivityResponse trackActivity(ActivityRequest request) {
		
		boolean isValidUser = userValidationService.validateUser(request.getUserId());
		
		if (!isValidUser) {
			throw new RuntimeException("Invalid User: "+request.getUserId());
		}
		
		Activity activity=Activity.builder()
				.userId(request.getUserId())
				.type(request.getType())
				.duration(request.getDuration())
				.caloriesBurned(request.getCaloriesBurned())
				.startTime(request.getStartTime())
				.additionalMetrices(request.getAdditionalMetrices())
				.build();
		Activity savedActivity=repo.save(activity);
		
		
		//Publish to RabbitMQ for AI Processing
		try {
			rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
			
		}catch(Exception e){
			log.error("Failed to publish activity to RabbitMQ : ",e);
		}
		return mapToResponse(savedActivity);
		
	}
	private ActivityResponse mapToResponse(Activity activity) {
		ActivityResponse response =new ActivityResponse();
		response.setId(activity.getId());
		response.setUserId(activity.getUserId());
		response.setType(activity.getType());
		response.setDuration(activity.getDuration());
		response.setCaloriesBurned(activity.getCaloriesBurned());
		response.setStartTime(activity.getStartTime());
		response.setAdditionalMetrices(activity.getAdditionalMetrices());
		response.setCreatedAt(activity.getCreatedAt());
		response.setUpdatedAt(activity.getUpdatedAt());
		return response;
	}


	public List<ActivityResponse> getUserActivities(String userId) {
	
		List<Activity> activities=repo.findByUserId(userId);
		return activities.stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}


	public ActivityResponse getActivityById(String activityId) {
		return repo.findById(activityId)
				.map(this::mapToResponse)
				.orElseThrow(()->new RuntimeException("Activity not found with id "+activityId));
	}

}
