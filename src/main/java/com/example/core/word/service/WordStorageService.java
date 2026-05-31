package com.example.core.word.service;

import com.example.core.word.infra.mapper.dto.WordInsertDto;
import com.example.core.word.service.dto.RefinedWordDto;
import com.example.core.word.infra.mapper.FrequencyMapper;
import com.example.core.word.infra.mapper.WordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordStorageService {
    private final WordMapper wordMapper;
    private final FrequencyMapper frequencyMapper;

    @Autowired
    public WordStorageService(WordMapper wordMapper, FrequencyMapper frequencyMapper) {
        this.wordMapper = wordMapper;
        this.frequencyMapper = frequencyMapper;
    }

    @Transactional
    public List<Long> saveWordList(List<RefinedWordDto> refinedWordDtos) {
        List<Long> savedIdList = new ArrayList<>();
        for (RefinedWordDto dto : refinedWordDtos) {
            WordInsertDto insertDto = dto.toInsertDto();
            Long existId = wordMapper.findByName(insertDto.getName());
            if(existId == null) {
                wordMapper.saveWord(insertDto);
                existId = insertDto.getId();
            }
            Integer frequency = dto.getFrequency();
            frequencyMapper.saveFrequency(frequency, existId);

            savedIdList.add(existId);
        }
        return savedIdList;
    }





}
