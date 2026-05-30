package com.example.core.word.service;

import com.example.core.word.domain.Frequency;
import com.example.core.word.domain.Word;
import com.example.core.word.mapper.WordMapper;
import com.example.core.word.reportdto.ReportResponseDto;
import com.example.core.word.mapper.FrequencyMapper;
import com.example.core.word.reportdto.WordInfoMapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {
    private final FrequencyMapper frequencyMapper;
    private final WordMapper wordMapper;

    @Autowired
    public ReportService(FrequencyMapper frequencyMapper, WordMapper wordMapper) {
        this.frequencyMapper = frequencyMapper;
        this.wordMapper = wordMapper;
    }

    public ReportResponseDto getWordReport(String id) {
        Long wordId = Long.valueOf(id);
        WordInfoMapperDto wordInfoMapperDto = wordMapper.findById(wordId);
        List<Integer> freqList = frequencyMapper.getFrequencyList(84, wordId);
        Frequency frequency = new Frequency(freqList);
        Word word = Word
                .builder()
                .id(wordInfoMapperDto.getId())
                .name(wordInfoMapperDto.getName())
                .meaning(wordInfoMapperDto.getMeaning())
                .example(wordInfoMapperDto.getExample())
                .frequency(frequency).build();
        String trend = word.calculateTrend();
        List<Integer> weeklyList = word.getWeeklyFrequency();
        return new ReportResponseDto(word, trend,weeklyList);
    }
}