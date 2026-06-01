package com.example.core.notification.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "today_word")
public class NotificationTodayWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")//테이블 컬럼명 구성하는거 보고 수정할 수도.
    private NotificationWord word;

    private LocalDate displayDate;
}
