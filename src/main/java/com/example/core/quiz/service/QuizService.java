package com.example.core.quiz.service;

import lombok.RequiredArgsConstructor;

import com.example.core.quiz.domain.Quiz;
import com.example.core.quiz.dto.QuizResponse;
import com.example.core.quiz.repository.QuizRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizResponse getQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀴즈Id입니다."));
        //단어 1개 조회용. 추가 기능 넣으면 필요할 거 같아서 만들어둠.
        QuizResponse response = QuizResponse.builder()
                .quizId(quiz.getId())
                .question(quiz.getQuestion())
                .answerNum(quiz.getAnswerNum())
                .explanation(quiz.getExplanation())
                .options(List.of(quiz.getOption1(), quiz.getOption2(), quiz.getOption3(),  quiz.getOption4()))
                .build();
        return response;
    }

    public List<QuizResponse> getRandomQuizzes(int limit) {
        return quizRepository.findRandomQuizzes(limit).stream()
                .map(quiz -> QuizResponse.builder()
                        .quizId(quiz.getId())
                        .question(quiz.getQuestion())
                        .answerNum(quiz.getAnswerNum())
                        .explanation(quiz.getExplanation())
                        .options(List.of(quiz.getOption1(), quiz.getOption2(), quiz.getOption3(), quiz.getOption4()))
                        .build()
                )
                .collect(Collectors.toList());
    }

}
