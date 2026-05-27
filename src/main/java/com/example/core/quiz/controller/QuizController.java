package com.example.core.quiz.controller;

import com.example.core.quiz.service.QuizService;
import com.example.core.quiz.dto.QuizResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;
    //퀴즈 1개 조회
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable Long quizId) {
        QuizResponse quizResponse = quizService.getQuiz(quizId);
        return ResponseEntity.ok(quizResponse);
    }

    //랜덤으로 퀴즈 10개 조회
    @GetMapping
    public ResponseEntity<List<QuizResponse>> getRandomQuizzes(
            @RequestParam(value = "limit") int limit
    ) {
        List<QuizResponse> responses = quizService.getRandomQuizzes(limit);
        return ResponseEntity.ok(responses);
    }
}
