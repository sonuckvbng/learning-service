package com.calcplay.learningservice.dto;
public record SessionStartResponse(String sessionId, QuestionDto currentQuestion, int totalQuestions) { }
