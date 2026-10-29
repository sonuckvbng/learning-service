package com.calcplay.learningservice.exception;

import com.calcplay.learningservice.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> validation(MethodArgumentNotValidException ex) {
        String message=ex.getBindingResult().getFieldErrors().stream().findFirst().map(e->e.getDefaultMessage()).orElse("Invalid request");
        return ResponseEntity.badRequest().body(new ApiErrorResponse(Instant.now(),400,"Validation failed",message));
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> status(ResponseStatusException ex) {
        int code=ex.getStatusCode().value();
        return ResponseEntity.status(ex.getStatusCode()).body(new ApiErrorResponse(Instant.now(),code,HttpStatus.valueOf(code).getReasonPhrase(),ex.getReason()==null?"Request failed":ex.getReason()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> unexpected(Exception ex) {
        return ResponseEntity.status(500).body(new ApiErrorResponse(Instant.now(),500,"Internal server error","The request could not be completed."));
    }
}
