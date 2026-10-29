package com.calcplay.learningservice.service;

import com.calcplay.learningservice.dto.*;
import com.calcplay.learningservice.entity.LearningSession;
import com.calcplay.learningservice.repository.LearningSessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class LearningService {
    private final LearningSessionRepository repository;
    private final Map<String, GameState> activeGames = new ConcurrentHashMap<>();

    public LearningService(LearningSessionRepository repository) { this.repository=repository; }

    public SessionStartResponse start(StartRequest request) {
        String category=request.category().toUpperCase(Locale.ROOT);
        String difficulty=request.difficulty().toUpperCase(Locale.ROOT);
        if (!java.util.Set.of("ADDITION","SUBTRACTION","MULTIPLICATION","DIVISION").contains(category))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported question category");
        if (!java.util.Set.of("EASY","MEDIUM","HARD").contains(difficulty))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported difficulty");
        String id=UUID.randomUUID().toString();
        repository.save(new LearningSession(id,category,difficulty,request.totalQuestions()));
        GameState state=new GameState(id,category,difficulty,request.totalQuestions());
        activeGames.put(id,state);
        return new SessionStartResponse(id,state.next(),state.total);
    }

    public AnswerResponse submitAnswer(AnswerRequest request) {
        GameState state=activeGames.get(request.sessionId());
        if (state==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found or its active state expired");
        if (state.current==null || !state.current.questionId().equals(request.questionId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This question is no longer active");
        int expectedAnswer=state.current.answer();
        boolean correct=request.answer()==expectedAnswer;
        state.answered++;
        if (correct) state.score++;
        boolean finished=state.answered>=state.total;
        QuestionDto next=finished?null:state.next();
        LearningSession session=repository.findBySessionId(state.id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Session not found"));
        session.updateProgress(state.answered,state.score,finished);
        repository.save(session);
        if (finished) activeGames.remove(state.id);
        return new AnswerResponse(correct,expectedAnswer,state.score,state.answered,state.total,next,finished);
    }

    @Transactional(readOnly = true)
    public SessionDto getSession(String id) {
        LearningSession session=repository.findBySessionId(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Session not found"));
        return new SessionDto(session.getSessionId(),session.getCategory(),session.getDifficulty(),session.getTotalQuestions(),
                session.getAnswered(),session.getCorrectAnswers(),session.getStatus(),session.getCreatedAt());
    }
}
