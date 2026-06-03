package com.example.core.subscription.controller;

import com.example.core.subscription.dto.SubscriptionRequest;
import com.example.core.subscription.service.SubscriptionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Void> createSubscription(@RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionService.createSubscription(subscriptionRequest);
        return ResponseEntity.ok().build();
    }
}
