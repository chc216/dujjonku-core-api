package com.example.core.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.SQLRestriction;//단어 삭제된 것들 제외하고, 삭제 안된 것들만 가져오기 위한 용도

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL") //삭제 안된 퀴즈들만 가져옴.
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //FK : 단어DB에 저장된 테이터 관리자가 조회api호출해서 wordId 확보 후 퀴즈 등록 시 다른 컬럼들과 동일하게 body에서 받아올 거임.
    @Column(nullable = false)
    private Long wordId;
    //FK : 프론트엔드 서버에서 들고 있는 관리자Id(JWT)들어있는 헤더 가로채서 퀴즈 등록 시 받아올 거임.
    @Column(nullable = false)
    private Long adminId;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private int answerNum;

    @Column(nullable = false)
    private String option1;

    @Column(nullable = false)
    private String option2;

    @Column(nullable = false)
    private String option3;

    @Column(nullable = false)
    private String option4;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public Quiz(Long wordId, Long adminId, String question, int answerNum, String option1, String option2, String option3, String option4, String explanation) {
        this.wordId = wordId;
        this.adminId = adminId;
        this.question = question;
        this.answerNum = answerNum;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.explanation = explanation;
        this.createdAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

}
