package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.QuestionsWrittenByParticularUser;
import com.example.demo.dto.UserPerformanceDTO;
import com.example.demo.entity.UserPerformance;

public interface UserPerformanceService {
	UserPerformance saveUserPerformance(UserPerformance userPerformance);

	UserPerformance updateUserPerformance(int id, UserPerformance updatedPerformance);

	void deleteUserPerformance(int id);

	Optional<UserPerformance> getUserPerformanceById(int id);

	List<UserPerformanceDTO> getAllUserPerformanceBasedOnScoreAndTime();

	List<QuestionsWrittenByParticularUser> getQuestionsByUserTokenId(String tokenId);
}
