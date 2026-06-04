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

}
