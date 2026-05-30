package com.example.core.word.reportdto;

import lombok.Getter;

//mapper -> service
@Getter
public class WordInfoMapperDto {
    private Long id;
    private String name;
    private String meaning;
    private String example;
}
