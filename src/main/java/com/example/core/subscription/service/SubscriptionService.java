package com.example.core.subscription.service;

import lombok.RequiredArgsConstructor;

import com.example.core.subscription.domain.Subscription;
import com.example.core.subscription.dto.SubscriptionRequest;
import com.example.core.subscription.repository.SubscriptionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public void createSubscription(SubscriptionRequest request) {
        Subscription newSubscription = Subscription.builder()
                .email(request.getEmail())
                .consent(request.isConsent())
                .build();
        subscriptionRepository.save(newSubscription);
    }
}
