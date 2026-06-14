package com.example.core.word.infra.mysql.mapper.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodayWordDto {
    private Long id;
    private String name;
    private String meaning;
    private String example;
}
