package com.example.core.quiz.service;

import com.example.core.quiz.domain.Quiz;
import com.example.core.quiz.dto.AdminQuizResponse;
import com.example.core.quiz.dto.AdminQuizPageResponse;
import com.example.core.quiz.repository.QuizRepository;
import com.example.core.quiz.dto.AdminQuizRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQuizService {
    private final QuizRepository quizRepository;

    //관리자용 퀴즈 목록 페이지 조회(JPA에서 제공하는 Page객체 적극 활용했습니다.)
    public AdminQuizPageResponse getAdminQuizzes(Pageable pageable) {
        //페이지 통계 정보 및 단어 리스트 가져오기.
        Page<Quiz> quizzesPage = quizRepository.findByAllQuizzes(pageable);
        //가져온 페이지 객체의 통계정보를 제외한 나머지 단어 정보들 관리자용 dto로 담기
        List<AdminQuizResponse> quizResponses = quizzesPage.getContent().stream()
                .map(quiz -> AdminQuizResponse.builder()
                        .quizId(quiz.getId())
                        .wordId(quiz.getWordId())
                        .question(quiz.getQuestion())
                        .option1(quiz.getOption1())
                        .option2(quiz.getOption2())
                        .option3(quiz.getOption3())
                        .option4(quiz.getOption4())
                        .answerNum(quiz.getAnswerNum())
                        .explanation(quiz.getExplanation())
                        .createdAt(quiz.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        //dto리스트와 페이지 객체를 참조하여 최종 AdminQuizPageResponse DTO에 담아서 반환
        AdminQuizPageResponse finalResponse = AdminQuizPageResponse.builder()
                .totalElements(quizzesPage.getTotalElements())
                .totalPages(quizzesPage.getTotalPages())
                .currentPage((quizzesPage.getNumber()) + 1)
                .pageSize(quizzesPage.getSize())
                .quizzes(quizResponses)
                .build();
        return finalResponse;

    }

    //관리자용 퀴즈 등록
    @Transactional
    public void createQuiz(AdminQuizRequest request, Long adminId) {
        Quiz newQuiz = Quiz.builder()
                .wordId(request.getWordId())
                .adminId(adminId)
                .question(request.getQuestion())
                .answerNum(request.getAnswerNum())
                .option1(request.getOption1())
                .option2(request.getOption2())
                .option3(request.getOption3())
                .option4(request.getOption4())
                .explanation(request.getExplanation())
                .build();
        quizRepository.save(newQuiz);
    }

    //관리자용 퀴즈 삭제
    @Transactional
    public void deleteQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀴즈입니다."));
        quiz.delete();
    }
}
