package com.example.core.word.service.dto;

import com.example.core.word.infra.mapper.dto.WordInsertDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefinedWordDto {
    private String name;
    private String meaning;
    private String example;
    @Setter
    private Integer frequency;

    public WordInsertDto toInsertDto(){
        return new WordInsertDto(name, meaning, example);
    }

}
