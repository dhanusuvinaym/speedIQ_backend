package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Guidelines;
import com.example.demo.repository.GuidelineRepository;
import com.example.demo.service.GuidelineService;

@Service
public class GuidelineServiceImpl implements GuidelineService {

    @Autowired
    private GuidelineRepository guidelineRepository;

    @Override
    public List<Guidelines> getContent() {
        // Assuming only one guideline content exists for simplicity
        return guidelineRepository.findAll();
    }

    @Override
    public void updateContent(Long id, String newContent) {
        Optional<Guidelines> guidelineOptional = guidelineRepository.findById(id);
        if (guidelineOptional.isPresent()) {
            Guidelines guideline = guidelineOptional.get();
            guideline.setContent(newContent);
            guidelineRepository.save(guideline);
        } else {
            throw new RuntimeException("Content with ID " + id + " not found");
        }
    }

    @Override
    public void deleteContent(Long id) {
        if (guidelineRepository.existsById(id)) {
            guidelineRepository.deleteById(id);
        } else {
            throw new RuntimeException("Content with ID " + id + " not found");
        }
    }

    @Override
    public void insertContent(String content) {
        Guidelines newGuideline = new Guidelines();
        newGuideline.setContent(content);
        guidelineRepository.save(newGuideline);
    }

    @Override
    public Optional<String> getContentById(Long id) {
        return guidelineRepository.findById(id).map(Guidelines::getContent);
    }
}

