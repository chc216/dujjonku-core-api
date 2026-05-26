package com.example.core.report.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

//service에서 조립 후 -> controller로 데이터 이동
@Getter
public class ReportResponseDto {
    private Long id;
    private String word;
    private String definition;
    private String example;
    private String trend;
    private Map<String, Integer> frequency;

    public ReportResponseDto(WordInfoDto wordInfoDto, Map<String, Integer> frequency) {
        this.id = wordInfoDto.getId();
        this.word = wordInfoDto.getWordName();
        this.definition = wordInfoDto.getDefinition();
        this.example = wordInfoDto.getExample();
        this.trend = wordInfoDto.getTrend();
        this.frequency = frequency;
    }


}
