package com.example.core.word.infra.mysql.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingInfoDto {
    private Long wordId;
    private String name;
    private String meaning;
    private String example;
    private Integer rank;
    private String trend;
}
