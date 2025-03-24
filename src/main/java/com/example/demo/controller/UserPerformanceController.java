package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.QuestionsWrittenByParticularUser;
import com.example.demo.dto.UserPerformanceDTO;
import com.example.demo.entity.UserPerformance;
import com.example.demo.service.UserPerformanceService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/userperformance")
public class UserPerformanceController {

	@Autowired
	private UserPerformanceService userPerformanceService;

	@PostMapping("/save")
	public UserPerformance saveUserPerformance(@RequestBody UserPerformance userPerformance) {
		return userPerformanceService.saveUserPerformance(userPerformance);
	}

	@PutMapping("/update/{id}")
	public UserPerformance updateUserPerformance(@PathVariable("id") int id,
			@RequestBody UserPerformance updatedPerformance) {
		return userPerformanceService.updateUserPerformance(id, updatedPerformance);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUserPerformance(@PathVariable("id") int id) {
		userPerformanceService.deleteUserPerformance(id);
		return "User performance with ID " + id + " deleted successfully!";
	}

	@GetMapping("/get/{id}")
	public Optional<UserPerformance> getUserPerformanceById(@PathVariable("id") int id) {
		return userPerformanceService.getUserPerformanceById(id);
	}

	@GetMapping("/getAll")
	public List<UserPerformanceDTO> getAllUserPerformances() {
		return userPerformanceService.getAllUserPerformanceBasedOnScoreAndTime();
	}
	
	@GetMapping("/getQuestionsByTokenId/{tokenId}")
	public List<QuestionsWrittenByParticularUser> getQuestionsWrittenByUser(@PathVariable("tokenId")String tokenId){
		return userPerformanceService.getQuestionsByUserTokenId(tokenId);
	}
}
