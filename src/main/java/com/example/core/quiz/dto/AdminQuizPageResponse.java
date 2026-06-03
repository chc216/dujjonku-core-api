package com.example.core.quiz.dto;

import lombok.Getter;
import lombok.Builder;

import java.util.List;

@Getter
@Builder
public class AdminQuizPageResponse {
    private Long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private List<AdminQuizResponse> quizzes;
}
