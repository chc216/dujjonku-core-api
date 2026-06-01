package com.example.core.word.controller.dto;

import com.example.core.word.service.dto.RawDataDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class CrawledDataRequestDto {
    @NotEmpty
    private String keyword;
    @NotEmpty
    private Map<String, Integer> platformFrequencies;
    @NotEmpty
    private List<String> originalExamples;

    public RawDataDto toRawData() {
        return new RawDataDto(keyword, originalExamples, platformFrequencies.get("twitter"));
    }
}
