package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Analysis;
import com.example.demo.serviceImpl.AnalysisServiceImpl;

@RestController
@RequestMapping("/api/analysis")
//@CrossOrigin("http://localhost:3000")
//@CrossOrigin("http://speediq.com.s3-website.eu-north-1.amazonaws.com/")
public class AnalysisController {

	@Autowired
	public AnalysisServiceImpl analysisServiceImpl;

	@GetMapping("/getAll/{id}")
	public List<Analysis> getAllAnalysisByUserId(@PathVariable("id") int id) {
		return analysisServiceImpl.getAllAnalysisBasedOnUserId(id);
	}

	@PostMapping("/save/{id}")
	public ResponseEntity<?> saveAllQuestionsWithSelectedOptionForUserId(@PathVariable("id") int id,
			@RequestBody List<Analysis> analysis) {
		for (Analysis a : analysis) {
			a.setUser_id(id);
			analysisServiceImpl.save(a);
		}
		return ResponseEntity.ok("All questions and selected options saved successfully!");
	}
	
	@GetMapping("/deleteAll")
	public void deleteAll() {
		analysisServiceImpl.deleteAllrows();
	}
}
