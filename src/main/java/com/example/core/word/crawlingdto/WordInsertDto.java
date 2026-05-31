package com.example.core.word.crawlingdto;

import lombok.AllArgsConstructor;
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
