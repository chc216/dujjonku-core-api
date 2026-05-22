package com.example.core.trend.domain;

public enum TrendIndicator {
    Hot, Neutral, Cold;
    public static TrendIndicator getTrendIndicator(int before, int after) {
        int ratio = (int)((double)after/before * 100);
        if(ratio > 60) {
            return TrendIndicator.Hot;
        } else if(ratio > 30) {
            return TrendIndicator.Neutral;
        } else {
            return TrendIndicator.Cold;
        }
    }
}
