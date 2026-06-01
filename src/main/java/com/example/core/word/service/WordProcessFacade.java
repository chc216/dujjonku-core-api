package com.example.core.word.service;


import com.example.core.word.service.dto.RawDataDto;
import com.example.core.word.service.dto.RefinedWordDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WordProcessFacade {
    private final WordStorageService wordStorageService;
    private final AiRefiner aiRefiner;

    public void process(List<RawDataDto> rawDataList) {
        List<RefinedWordDto> refinedWordDtoList = aiRefiner.refine(rawDataList);
        wordStorageService.saveWordList(refinedWordDtoList);
    }
}
