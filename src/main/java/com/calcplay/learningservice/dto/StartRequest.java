package com.calcplay.learningservice.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
public record StartRequest(@NotBlank String category, @NotBlank String difficulty, @Min(1) @Max(20) int totalQuestions) { }
