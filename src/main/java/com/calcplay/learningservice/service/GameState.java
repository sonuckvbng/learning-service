package com.calcplay.learningservice.service;

import com.calcplay.learningservice.dto.QuestionDto;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

final class GameState {
    final String id, category, difficulty;
    final int total;
    int answered = 0;
    int score = 0;
    QuestionDto current;
    private final Random random = new Random();

    GameState(String id, String category, String difficulty, int total) {
        this.id=id; this.category=category; this.difficulty=difficulty; this.total=total;
    }
    QuestionDto next() {
        int max = switch (difficulty.toUpperCase(Locale.ROOT)) { case "HARD" -> 100; case "MEDIUM" -> 50; default -> 12; };
        int a=random.nextInt(max)+1, b=random.nextInt(max)+1, answer;
        String op;
        switch (category.toUpperCase(Locale.ROOT)) {
            case "SUBTRACTION" -> { if (b>a) { int t=a; a=b; b=t; } op="−"; answer=a-b; }
            case "MULTIPLICATION" -> { int factorMax=difficulty.equalsIgnoreCase("HARD")?20:12; a=random.nextInt(factorMax)+1; b=random.nextInt(factorMax)+1; op="×"; answer=a*b; }
            case "DIVISION" -> { b=random.nextInt(12)+1; answer=random.nextInt(12)+1; a=b*answer; op="÷"; }
            case "ADDITION" -> { op="+"; answer=a+b; }
            default -> throw new IllegalArgumentException("Unsupported category. Use ADDITION, SUBTRACTION, MULTIPLICATION, or DIVISION.");
        }
        current=new QuestionDto(id, UUID.randomUUID().toString(), a+" "+op+" "+b, answer, category);
        return current;
    }
}
