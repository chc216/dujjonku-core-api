package com.example.core.trend.domain;

import java.util.List;
import java.util.Map;

public class StaticInfo {
    private final List<Integer> weeklyFrequency;
    private final Map<String, Integer> platformFrequency;

    public StaticInfo(List<Integer> weeklyFrequency, Map<String, Integer> platformFrequency) {
        this.weeklyFrequency = weeklyFrequency;
        this.platformFrequency = platformFrequency;
    }

    public Integer getPlatformFrequency(String platform) {
        if (platformFrequency.containsKey(platform)) {
            return platformFrequency.get(platform);
        }
        return 0;
    }

    public TrendIndicator calculateTrend() {
        if (weeklyFrequency.size() >= 2) {
            return TrendIndicator.getTrendIndicator(weeklyFrequency.get(0), weeklyFrequency.get(1));
        }
        return TrendIndicator.Hot;
    }

    public List<Integer> getFrequency() {
        return weeklyFrequency;
    }
}



//필요한 기능
//1. 통계를 받아서 저장한다.
//2. 메인 디비에서 매일 상위 10가지의 단어를 가져와서 레디스에 저장한다.
//3. 레디스에서 단어를 가져와서 반환한다.
//4. 특정 단어에 대해 조회 후 통계 데이터를 가공하여 보내준다.
//5.