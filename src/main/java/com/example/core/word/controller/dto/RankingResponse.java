package com.example.core.word.controller.dto;

import com.example.core.word.service.dto.RankingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingResponse {
    private Long id;
    private Integer rank;
    private String word;
    private String meaning;
    private String trend;

    public static RankingResponse from(RankingDto ranking) {
        return new RankingResponse(ranking.getWordId(), ranking.getRank(), ranking.getName(), ranking.getMeaning(), ranking.getTrend());
    }
}

