package com.calcplay.learningservice.dto;
import java.time.Instant;
public record SessionDto(String sessionId, String category, String difficulty, int totalQuestions, int answered, int correctAnswers, String status, Instant createdAt) { }
