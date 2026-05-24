package com.example.core.quiz.repository;

import com.example.core.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    //랜덤추출은 JPA안쓰고 native query로 가져옴.
    @Query(value = "SELECT * FROM quiz WHERE deleted_at IS NULL ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Quiz> findRandomQuizzes(@Param("limit") int limit);
}
