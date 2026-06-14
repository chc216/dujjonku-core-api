package com.example.core.word.controller;
import com.example.core.word.controller.dto.TodayWordResponse;
import com.example.core.word.infra.mysql.mapper.dto.TodayWordDto;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import com.example.core.word.service.TodayWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 오늘의 단어 조회 컨트롤러
@RestController
@RequiredArgsConstructor
public class TodayWordController {
    private final TodayWordService todayWordService;

    @GetMapping("/today")
    public List<TodayWordResponse> getTodayWords() {
        List<TodayWordDto> todayWords = todayWordService.getTodayWords();
        return todayWords.stream()
                .map(TodayWordResponse::from)
                .toList();
    }
}
