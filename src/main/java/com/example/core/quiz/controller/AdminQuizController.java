package com.example.core.quiz.controller;

import com.example.core.quiz.dto.AdminQuizRequest;
import com.example.core.quiz.service.AdminQuizService;
import com.example.core.quiz.dto.AdminQuizPageResponse;

import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/quizzes")
public class AdminQuizController {

    private final AdminQuizService adminQuizService;
    //관리자용 퀴즈 목록 페이징 조회 API
    @GetMapping
    public ResponseEntity<AdminQuizPageResponse> getAdminQuizzes(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        AdminQuizPageResponse response = adminQuizService.getAdminQuizzes(pageable);
        return ResponseEntity.ok(response);
    }
    //관리자용 퀴즈 등록 API
    @PostMapping
    public ResponseEntity<Void> createQuiz(@RequestBody AdminQuizRequest request) {
        adminQuizService.createQuiz(request, 1L);//나중에 토큰 넘겨줄건데, 임시로 1L 넘겨서 서비스 계층이랑 아구 맞춤.
        return ResponseEntity.ok().build();
    }
    //관리자용 퀴즈 삭제 API
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        adminQuizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();//204
    }
}
