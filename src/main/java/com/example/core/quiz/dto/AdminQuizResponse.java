package com.example.core.quiz.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class AdminQuizResponse {
    private Long quizId;
    private Long wordId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answerNum;
    private String explanation;
    private LocalDateTime createdAt;
}
