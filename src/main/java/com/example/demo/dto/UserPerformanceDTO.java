package com.example.demo.dto;

import java.sql.Timestamp;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPerformanceDTO {
	public int id;
	public String tokenid;
	public int score;
	public LocalTime examDurationTime;
	public LocalDateTime dateTime;

	public UserPerformanceDTO(int id, String tokenid, int score, Time exam_duration_time,
			Timestamp date_time) {
		this.id = id;
		this.tokenid = tokenid;
		this.score = score;
		this.examDurationTime = exam_duration_time.toLocalTime();
		this.dateTime = date_time.toLocalDateTime();
	}
}
