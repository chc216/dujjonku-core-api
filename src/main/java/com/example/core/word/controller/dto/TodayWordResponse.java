package com.example.core.word.controller.dto;

import com.example.core.word.infra.mysql.mapper.dto.TodayWordDto;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 오늘의 단어 조회 응답 DTO
// 프론트엔드는 word.word 필드를 사용하므로, DB DTO의 name -> word 로 매핑한다.
@Getter
@AllArgsConstructor
public class TodayWordResponse {
    private Long id;
    private String word;
    private String meaning;
    private String example;

    public static TodayWordResponse from(TodayWordDto dto) {
        return new TodayWordResponse(
                dto.getId(),
                dto.getName(),
                dto.getMeaning(),
                dto.getExample()
        );
    }
}