package com.calcplay.learningservice.dto;
public record AnswerResponse(boolean correct, int correctAnswer, int score, int answered, int totalQuestions, QuestionDto nextQuestion, boolean finished) { }
