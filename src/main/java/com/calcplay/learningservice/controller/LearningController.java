package com.calcplay.learningservice.controller;

import com.calcplay.learningservice.dto.*;
import com.calcplay.learningservice.service.LearningService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/learning")
public class LearningController {
    private static final Logger log = LoggerFactory.getLogger(LearningController.class);
    private final LearningService learningService;
    public LearningController(LearningService learningService) { this.learningService=learningService; }
    @PostMapping("/sessions")
    public SessionStartResponse start(@Valid @RequestBody StartRequest request) {
        log.info("Starting new session with request: {}", request);
        SessionStartResponse response = learningService.start(request);
        log.info("Session started successfully with response: {}", response);
        return response;
    }
    @PostMapping("/answers")
    public AnswerResponse submitAnswer(@Valid @RequestBody AnswerRequest request) {
        log.info("Submitting answer with request: {}", request);
        AnswerResponse response = learningService.submitAnswer(request);
        log.info("Answer submitted successfully with response: {}", response);
        return response;
    }
    @GetMapping("/sessions/{id}")
    public SessionDto getSession(@PathVariable String id) {
        log.info("Retrieving session with id: {}", id);
        SessionDto session = learningService.getSession(id);
        log.info("Session retrieved successfully: {}", session);
        return session;
    }
}
