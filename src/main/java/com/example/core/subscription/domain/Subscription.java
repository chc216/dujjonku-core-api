package com.example.core.subscription.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private boolean consent;

    @Builder
    public Subscription(String email, boolean consent) {
        this.email = email;
        this.consent = consent;
    }
}
