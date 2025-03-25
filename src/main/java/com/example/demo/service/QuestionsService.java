package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Questions;

public interface QuestionsService {

	void save(Questions q);
	
	List<Questions> getRandom10Questions();
	
	void deleteQuestion(int id);
	
	void updateQuestion(int id,Questions q);	
	
	List<Questions> getAllQuestions();
	
	void deleteAllrows();
}
