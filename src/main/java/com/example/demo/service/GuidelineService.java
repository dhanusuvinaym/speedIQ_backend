package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Guidelines;

public interface GuidelineService {
    List<Guidelines> getContent();
    void updateContent(Long id, String newContent);
    void deleteContent(Long id);
    void insertContent(String content);
    Optional<String> getContentById(Long id);
}
