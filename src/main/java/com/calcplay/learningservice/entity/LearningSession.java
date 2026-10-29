package com.calcplay.learningservice.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "learning_sessions", indexes = @Index(name = "idx_session_id", columnList = "sessionId", unique = true))
public class LearningSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String sessionId;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String difficulty;
    private int totalQuestions;
    private int answered;
    private int correctAnswers;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private Instant createdAt;
    private Instant updatedAt;

    protected LearningSession() { }
    public LearningSession(String sessionId, String category, String difficulty, int totalQuestions) {
        this.sessionId=sessionId; this.category=category; this.difficulty=difficulty; this.totalQuestions=totalQuestions;
        this.status="IN_PROGRESS"; this.createdAt=Instant.now();
    }
    public String getSessionId(){return sessionId;} public String getCategory(){return category;}
    public String getDifficulty(){return difficulty;} public int getTotalQuestions(){return totalQuestions;}
    public int getAnswered(){return answered;} public int getCorrectAnswers(){return correctAnswers;}
    public String getStatus(){return status;} public Instant getCreatedAt(){return createdAt;}
    public void updateProgress(int answered, int correctAnswers, boolean finished) {
        this.answered=answered; this.correctAnswers=correctAnswers; this.updatedAt=Instant.now();
        if (finished) this.status="COMPLETED";
    }
}
