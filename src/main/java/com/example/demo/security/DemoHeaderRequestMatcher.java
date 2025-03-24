package com.example.demo.security;


import org.springframework.security.web.util.matcher.RequestMatcher;
import jakarta.servlet.http.HttpServletRequest;

public class DemoHeaderRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        String demoHeader = request.getHeader("Demo");
        return "true".equalsIgnoreCase(demoHeader);
    }
}
