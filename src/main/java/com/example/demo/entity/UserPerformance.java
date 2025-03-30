package com.example.demo.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "userperformance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "user_id", nullable = false, length = 255)
    private int user_id;
    
    @Column(name = "score", nullable = false)
    private int score;
    
    @Column(name = "exam_duration_time", nullable = false)
    private LocalTime  exam_duration_time;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
}
