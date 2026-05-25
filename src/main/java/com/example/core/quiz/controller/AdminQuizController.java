package com.example.core.quiz.controller;

import com.example.core.quiz.service.AdminQuizService;
import com.example.core.quiz.dto.AdminQuizPageResponse;

import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/quizzes")
public class AdminQuizController {

    private final AdminQuizService adminQuizService;
    @GetMapping
    public ResponseEntity<AdminQuizPageResponse> getAdminQuizzes(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        AdminQuizPageResponse Response = adminQuizService.getAdminQuizzes(pageable);
        return ResponseEntity.ok(Response);
    }
}
