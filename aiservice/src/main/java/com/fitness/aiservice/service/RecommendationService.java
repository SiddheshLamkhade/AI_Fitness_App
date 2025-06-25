package com.fitness.aiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {
	private final RecommendationRepository repo;

	public List<Recommendation> getUserRecommendation(String userId) {
		return repo.findByUserId(userId);
	}

	public Recommendation getActivityRecommendation(String activityId) {
		return repo.findByActivityId(activityId)
				.orElseThrow(()->new RuntimeException("No Recommendation found for this"+ activityId));
	}	
}
