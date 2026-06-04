package com.example.core.word.controller;

import com.example.core.word.controller.dto.CrawledDataRequestDto;
import com.example.core.word.service.WordProcessFacade;
import com.example.core.word.service.dto.RawDataDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CrawlingController {
    private final WordProcessFacade wordProcessFacade;
    @Autowired
    public CrawlingController(WordProcessFacade wordProcessFacade) {
        this.wordProcessFacade = wordProcessFacade;
    }


    @PostMapping("/crawling")
    public ResponseEntity<String> saveCrawledData(@Valid @RequestBody List<CrawledDataRequestDto> crawledDataRequestDto) {
        List<RawDataDto> rawDataList = new ArrayList<>();
        for (CrawledDataRequestDto requestDto : crawledDataRequestDto) {
            rawDataList.add(requestDto.toRawData());
        }
        wordProcessFacade.process(rawDataList);

        return ResponseEntity.ok().build();
    }
}
