package com.example.core.word.service.dto;

import com.example.core.word.infra.mysql.mapper.dto.WordInsertDto;
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
    private String scenario;
    @Setter
    private Integer frequency;
}
