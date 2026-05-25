package com.example.core.quiz.repository;

import com.example.core.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    //랜덤추출은 JPA안쓰고 native query로 가져옴.
    @Query(value = "SELECT * FROM quiz WHERE deleted_at IS NULL ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Quiz> findRandomQuizzes(@Param("limit") int limit);
    //페이징 자동화를 위한 query(native query 대신 JPQL 하이버네이트가 번역해서 query 생성하게 해서, 안정성 높임, 카운트 쿼리도 생성)
    @Query(value = "SELECT q FROM Quiz q WHERE q.deletedAt IS NULL")
    Page<Quiz> findByAllQuizzes(Pageable pageable);
}
