package com.example.core.quiz.dto;

import lombok.Builder;
import lombok.*;
import java.util.List;

@Builder
@Getter
public class QuizResponse {
    private Long quizId;
    private String question;
    private List<String> options;
    private int answerNum;
    private String explanation;
}
