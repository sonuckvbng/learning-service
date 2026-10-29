package com.calcplay.learningservice.dto;
import java.time.Instant;
public record ApiErrorResponse(Instant timestamp, int status, String error, String message) { }
