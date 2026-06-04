package com.example.core.word.scheduler;


import com.example.core.word.service.TodayWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodaywordScheduler {
    private final TodayWordService todayWordService;

    @Scheduled(cron = "0 0 8 * * *", zone = "Asia/Seoul")
    public void schedule() {
        todayWordService.updateTodayWord();
    }
}
