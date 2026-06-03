package com.example.core.subscription.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscriptionRequest {
    private String email;
    private boolean consent;
}
