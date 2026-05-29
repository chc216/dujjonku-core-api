package com.example.core.word.reportdto;

import lombok.Getter;

//mapper -> service
@Getter
public class WordInfoDto {
    private Long id;
    private String wordName;
    private String definition;
    private String example;
    private String trend;
}
