package com.example.core.word.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RawDataDto {
    private String name;
    private List<String> exampleList;
    private Integer frequency;

    @Override
    public String toString(){
        return "{" +
                "\"name\": \"" + name + "\", " +
                "\"example\": " + exampleList +
                "}";
    }
}
