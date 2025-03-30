package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Guidelines;
import com.example.demo.service.GuidelineService;

//@CrossOrigin("http://speediq.com.s3-website.eu-north-1.amazonaws.com/")
@RestController
@RequestMapping("/api/guidelines")
public class GuidelineController {

    @Autowired
    private GuidelineService guidelineService;

    @GetMapping("/getAll")
    public List<Guidelines> getGuidelines() {
        return guidelineService.getContent();
    }

    @PostMapping("/insert")
    public String insertGuideline(@RequestBody String content) {
        guidelineService.insertContent(content);
        return "Content inserted successfully!";
    }

    @PutMapping("/{id}")
    public String updateGuideline(@PathVariable Long id,@RequestBody Map<String, String> requestBody) {
    	String content = requestBody.get("content");
    	guidelineService.updateContent(id, content);
        return "Content updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteGuideline(@PathVariable Long id) {
        guidelineService.deleteContent(id);
        return "Content deleted successfully!";
    }

    @GetMapping("/getbyId/{id}")
    public Optional<String> getGuidelineById(@PathVariable Long id) {
        return guidelineService.getContentById(id);
    }
}
