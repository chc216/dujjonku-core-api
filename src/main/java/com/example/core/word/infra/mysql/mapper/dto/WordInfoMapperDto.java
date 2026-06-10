package com.example.core.word.infra.mysql.mapper.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WordInfoMapperDto {
    private Long id;
    private String name;
    private String meaning;
    private String example;
    private String scenario;
}
