package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.UserPerformanceDTO;
import com.example.demo.entity.UserPerformance;

public interface UserPerformanceRepository extends JpaRepository<UserPerformance, Integer> {

	@Query(value = "select u.id,l.tokenid,u.score,u.exam_duration_time as examDurationTime,u.date_time as dateTime from userperformance as u\r\n"
			+ "inner join login as l\r\n"
			+ "where u.user_id=l.id order by score desc,exam_duration_time asc;", nativeQuery = true)
	List<UserPerformanceDTO> getUserPerformanceBasedOnScoreAndExamDurationTime();

	@Query(value = "call getQuestionsAttenedByUserId(:tokenId)", nativeQuery = true)
	List<Object[]> getQuestionsWrittenByUser(@Param("tokenId") String tokenId);
}
