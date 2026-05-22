package com.example.core.trend.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Word {
    private final String word;
    private final String meaning;
    private final String example;
    private final String definition;
    private final StaticInfo staticInfo;
    public Word(String word, String meaning, String example, String definition,StaticInfo staticInfo) {
        this.word = word;
        this.meaning = meaning;
        this.example = example;
        this.definition = definition;
        this.staticInfo = staticInfo;
    }

    public TrendIndicator getTrendIndicator() {
        return staticInfo.calculateTrend();
    }

    public List<Integer> getFrequency() {
        return staticInfo.getFrequency();
    }

    public int getWordHashing() {
        return word.hashCode();
    }
}
