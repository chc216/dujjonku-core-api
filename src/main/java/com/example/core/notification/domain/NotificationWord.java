package com.example.core.notification.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "word")//테이블 만들면 이름 변경 가능성 있음.
public class NotificationWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wordName;
    private String definition;
    private String example;

}
