package com.calcplay.learningservice.dto;
import jakarta.validation.constraints.NotBlank;
public record AnswerRequest(@NotBlank String sessionId, @NotBlank String questionId, int answer) { }
