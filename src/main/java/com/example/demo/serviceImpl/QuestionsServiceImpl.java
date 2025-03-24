package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Questions;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.service.QuestionsService;

@Service
public class QuestionsServiceImpl implements QuestionsService {

	@Autowired
	private QuestionsRepository questionRepository;

	@Override
	public void save(Questions q) {
		questionRepository.save(q);
	}

	@Override
	public List<Questions> getRandom10Questions() {
		List<Questions> allQuestions = questionRepository.findAll();
		Random random = new Random();
		return allQuestions.stream().sorted((q1, q2) -> random.nextInt(2) - 1).limit(10)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteQuestion(int id) {
		questionRepository.deleteById(id);
	}

	@Override
	public void updateQuestion(int id, Questions q) {
		Optional<Questions> existingQuestion = questionRepository.findById(id);
		if (existingQuestion.isPresent()) {
			Questions updatedQuestion = existingQuestion.get();
			updatedQuestion.setQuestion(q.getQuestion());
			updatedQuestion.setOptionA(q.getOptionA());
			updatedQuestion.setOptionB(q.getOptionB());
			updatedQuestion.setOptionC(q.getOptionC());
			updatedQuestion.setOptionD(q.getOptionD());
			updatedQuestion.setCorrectOption(q.getCorrectOption());
			questionRepository.save(updatedQuestion);
		}
	}

	@Override
	public List<Questions> getAllQuestions() {
		return questionRepository.findAll();
	}
}
