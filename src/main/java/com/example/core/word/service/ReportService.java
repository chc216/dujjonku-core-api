package com.example.core.word.service;

import com.example.core.word.domain.Frequency;
import com.example.core.word.domain.Word;
import com.example.core.word.infra.mysql.mapper.WordMapper;
import com.example.core.word.service.dto.WordReportDto;
import com.example.core.word.infra.mysql.mapper.FrequencyMapper;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final FrequencyMapper frequencyMapper;
    private final WordMapper wordMapper;

    @Transactional
    public WordReportDto getWordReport(String id) {
        Long wordId = Long.valueOf(id);
        WordInfoMapperDto wordInfoMapperDto = wordMapper.findById(wordId);
        List<Integer> freqList = frequencyMapper.findFrequencyListByWordId(84, wordId);
        Frequency frequency = new Frequency(freqList);
        Word word = Word
                .builder()
                .id(wordInfoMapperDto.getId())
                .name(wordInfoMapperDto.getName())
                .meaning(wordInfoMapperDto.getMeaning())
                .example(wordInfoMapperDto.getExample())
                .frequency(frequency).build();
        String trend = frequency.calculateTrend();
        List<Integer> weeklyList = word.getWeeklyFrequency();
        return new WordReportDto(word, trend,weeklyList);
    }
}