package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Analysis;
import com.example.demo.repository.AnalysisRepository;
import com.example.demo.service.AnalysisService;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	public AnalysisRepository analysisRepository;

	@Override
	public void save(Analysis a) {
		analysisRepository.save(a);
	}

	@Override
	public List<Analysis> getAllAnalysisBasedOnUserId(int id) {
		return analysisRepository.getAllAnalysisByUserId(id);
	}

	@Override
	public void deleteAllrows() {
		analysisRepository.deleteAll();
	}

}
