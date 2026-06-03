package com.example.core.quiz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminQuizRequest {
    private Long wordId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answerNum;
    private String explanation;
}
