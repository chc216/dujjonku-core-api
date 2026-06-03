package com.example.core.word.infra.mysql.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordFrequencyDto {
    private Long wordId;
    private Integer frequency;
}
