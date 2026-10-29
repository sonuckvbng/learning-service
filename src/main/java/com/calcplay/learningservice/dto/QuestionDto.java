package com.calcplay.learningservice.dto;
public record QuestionDto(String sessionId, String questionId, String prompt, int answer, String category) { }
