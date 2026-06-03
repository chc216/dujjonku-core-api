package com.example.core.word.infra.mysql.mapper.dto;

import lombok.Getter;

@Getter
public class WordInsertDto {
    Long id;
    String name;
    String meaning;
    String example;

    public WordInsertDto(String name, String meaning, String example) {
        this.name = name;
        this.meaning = meaning;
        this.example = example;
    }
}
