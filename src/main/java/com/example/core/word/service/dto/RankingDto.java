package com.example.core.word.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingDto {
    private Long wordId;
    private String name;
    private String meaning;
    private String example;
    private Integer rank;
    private String trend;
}