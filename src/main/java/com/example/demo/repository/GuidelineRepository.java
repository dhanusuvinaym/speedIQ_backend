package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Guidelines;

public interface GuidelineRepository extends JpaRepository<Guidelines, Long> {
}

