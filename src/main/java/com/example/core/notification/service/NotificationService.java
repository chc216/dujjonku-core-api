package com.example.core.notification.service;

import com.example.core.notification.domain.NotificationTodayWord;
import com.example.core.notification.repository.NotificationRepository;
import com.example.core.subscription.domain.Subscription;
import com.example.core.subscription.repository.SubscriptionRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final JavaMailSender mailSender;

    @Transactional
    @Scheduled(cron = "*/10 * * * * *")
    public void send() {
        List<Subscription> subscribers = subscriptionRepository.findByConsentTrue();
        LocalDate today = LocalDate.now();
        List<NotificationTodayWord> todayWords = notificationRepository.findByDisplayDate(today);

        if (todayWords.isEmpty() || subscribers.isEmpty()) {
            return;
        }

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("오늘의 단어가 도착했습니다!\n\n");

        for (int i = 0; i < todayWords.size(); i++) {
            NotificationTodayWord todayWord = todayWords.get(i);
            String wordName = todayWord.getWord().getName();
            String definition = todayWord.getWord().getMeaning();
            String example = todayWord.getWord().getExample();

            emailContent.append(i + 1).append(". ").append(wordName).append(" : ").append(definition).append("\n\n")
                    .append("예문 : ").append(example).append("\n");

        }

        System.out.println("\n================메일 발송 스케쥴러 가동=================");
        for (Subscription subscriber : subscribers) {
            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(subscriber.getEmail());
                mailMessage.setSubject("오늘이 단어가 도착했습니다!");
                mailMessage.setText(emailContent.toString());

                mailSender.send(mailMessage);
                System.out.println("메일 전송 성공 -> To " + subscriber.getEmail());
            }catch (Exception e) {
                System.out.println("메일 발송 실패 -> To " + subscriber.getEmail() + "(사유 : "+ e.getMessage() +")");
            }
        }
        System.out.println("=============총 : " + subscribers.size() + "명 발송 완료=====");

    }
}
