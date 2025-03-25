package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Analysis;

public interface AnalysisService {
	void save(Analysis a);
	List<Analysis> getAllAnalysisBasedOnUserId(int id);
	
	void deleteAllrows();
}
