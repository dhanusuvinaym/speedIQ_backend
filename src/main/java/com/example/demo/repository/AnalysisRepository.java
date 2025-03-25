package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {

	@Query(value = "select * from analysis where user_id=:id", nativeQuery = true)
	List<Analysis> getAllAnalysisByUserId(@Param("id") int id);

}
