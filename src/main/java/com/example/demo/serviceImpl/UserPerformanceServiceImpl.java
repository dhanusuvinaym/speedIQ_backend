package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.QuestionsWrittenByParticularUser;
import com.example.demo.dto.UserPerformanceDTO;
import com.example.demo.entity.UserPerformance;
import com.example.demo.repository.UserPerformanceRepository;
import com.example.demo.service.UserPerformanceService;

@Service
public class UserPerformanceServiceImpl implements UserPerformanceService {

	@Autowired
	private UserPerformanceRepository userPerformanceRepository;

	@Override
	public UserPerformance saveUserPerformance(UserPerformance userPerformance) {
		return userPerformanceRepository.save(userPerformance);
	}

	@Override
	public UserPerformance updateUserPerformance(int id, UserPerformance updatedPerformance) {
		Optional<UserPerformance> existingPerformance = userPerformanceRepository.findById(id);
		if (existingPerformance.isPresent()) {
			UserPerformance performance = existingPerformance.get();
			performance.setUser_id(updatedPerformance.getUser_id());
			performance.setScore(updatedPerformance.getScore());
			performance.setExam_duration_time(updatedPerformance.getExam_duration_time());
			performance.setDateTime(updatedPerformance.getDateTime());
			return userPerformanceRepository.save(performance);
		} else {
			throw new RuntimeException("UserPerformance with ID " + id + " not found");
		}
	}

	@Override
	public void deleteUserPerformance(int id) {
		userPerformanceRepository.deleteById(id);
	}

	@Override
	public Optional<UserPerformance> getUserPerformanceById(int id) {
		return userPerformanceRepository.findById(id);
	}
	
	@Override
	public List<UserPerformanceDTO> getAllUserPerformanceBasedOnScoreAndTime() {
		return userPerformanceRepository.getUserPerformanceBasedOnScoreAndExamDurationTime();
	}
	
	@Override
	public List<QuestionsWrittenByParticularUser> getQuestionsByUserTokenId(String tokenId) {
		List<Object[]> rawData = userPerformanceRepository.getQuestionsWrittenByUser(tokenId);
	    List<QuestionsWrittenByParticularUser> results = new ArrayList<>();

	    for (Object[] row : rawData) {
	        String question = (String) row[0];
	        String optionA = (String) row[1];
	        String optionB = (String) row[2];
	        String optionC = (String) row[3];
	        String optionD = (String) row[4];
	        
	        // Cast or parse correctOption and optionSelected as char
	        char correctOption = row[5] != null ? row[5].toString().charAt(0) : '\0'; // Ensure it's a valid char
	        char optionSelected = row[6] != null ? row[6].toString().charAt(0) : '\0';

	        boolean finalOutput = row[7] != null && ((Long) row[7]) == 1L;

	        // Create DTO and add it to the results
	        QuestionsWrittenByParticularUser questionData = new QuestionsWrittenByParticularUser(
	            question, optionA, optionB, optionC, optionD, correctOption, optionSelected, finalOutput
	        );
	        results.add(questionData);
	    }

	    return results;
	}
}
