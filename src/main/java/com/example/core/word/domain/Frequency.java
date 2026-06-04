package com.example.core.word.domain;


import java.util.ArrayList;
import java.util.List;

public class Frequency {
    private List<Integer> frequencyList;
    private Integer size;

    public Frequency(List<Integer> frequencyList) {
        this.frequencyList = frequencyList;
        this.size = frequencyList.size();
    }


    public Integer getToday() {
        if (frequencyList.isEmpty()) {
            return 0;
        }
        return frequencyList.get(0);
    }

    public int getYesterday() {
        if (frequencyList.size() < 2) {
            return 0;
        }
        return frequencyList.get(1);
    }

    //트랜드 지수 계산을 위해 임시로 어제 대비 오늘 빈도수의 비율로 계산하는 로직 사용
    public List<Integer> convertToWeekly(int weekCount) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < weekCount; i++) {
            int weeklyFrequencySum = 0;
            for (int j = 0; j < 7; j++) {
                int dailyIndex = 7 * i + j;
                if (frequencyList.size() <= dailyIndex) {
                    weeklyFrequencySum += 0;
                } else {
                    weeklyFrequencySum += frequencyList.get(dailyIndex);
                }
            }
            result.add(weeklyFrequencySum);
        }
        return result;
    }
}