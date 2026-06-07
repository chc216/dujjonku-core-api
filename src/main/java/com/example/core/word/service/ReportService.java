package com.example.core.word.service;

import com.example.core.word.domain.Frequency;
import com.example.core.word.domain.Word;
import com.example.core.word.infra.mysql.mapper.WordMapper;
import com.example.core.word.service.dto.VoteDto;
import com.example.core.word.service.dto.WordReportDto;
import com.example.core.word.infra.mysql.mapper.FrequencyMapper;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import jakarta.persistence.EntityNotFoundException;
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
    public WordReportDto getWordReport(Long wordId) {
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

    @Transactional
    public Long increaseLike(Long id) {
        if(!wordMapper.existById(id)) {
            throw new EntityNotFoundException("Word not found");
        }
        wordMapper.increaseLikeById(id);
        return wordMapper.findLikeById(id);
    }
    @Transactional
    public Long increaseDislike(Long id) {
        if(!wordMapper.existById(id)) {
            throw new EntityNotFoundException("Word not found");
        }
        wordMapper.increaseDislikeById(id);
        return wordMapper.findDislikeById(id);
    }

    @Transactional
    public VoteDto getVoteById(Long id) {
        if(!wordMapper.existById(id)) {
            throw new EntityNotFoundException("Word not found");
        }
        return wordMapper.findVoteById(id);
    }

}