package com.calcplay.learningservice.repository;
import com.calcplay.learningservice.entity.LearningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface LearningSessionRepository extends JpaRepository<LearningSession, Long> {
    Optional<LearningSession> findBySessionId(String sessionId);
}
