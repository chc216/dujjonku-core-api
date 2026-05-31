package com.example.core.word.crawlingdto;

import com.example.core.word.domain.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordSaveRequest {
    private String name;
    private String meaning;
    private String example;
    private Integer frequency;

    public WordInsertDto toInsertDto(){
        return new WordInsertDto(name, meaning, example);
    }
}
