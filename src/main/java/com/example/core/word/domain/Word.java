package com.example.core.word.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Word {
    private Long id;
    private String name;
    private String meaning;
    private String example;
    private Frequency frequency;

    public List<Integer> getWeeklyFrequency() {
        return frequency.convertToWeekly(12);

    }
    public String calculateTrend() {
        int todayFreq = frequency.getToday();
        int yesterdayFreq = frequency.getYesterday();
        if (yesterdayFreq == 0) {
            yesterdayFreq = 1;
        }
        double ratio = (double)todayFreq / yesterdayFreq;
        if (ratio > 1.3) {
            return "hot";
        } else if (ratio > 0.8){
            return "neutral";
        } else {
            return "cold";
        }
    }
}
