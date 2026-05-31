package com.example.core.word.service;

import com.example.core.word.crawlingdto.WordInsertDto;
import com.example.core.word.crawlingdto.WordSaveRequest;
import com.example.core.word.mapper.FrequencyMapper;
import com.example.core.word.mapper.WordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlingService {
    private final WordMapper wordMapper;
    private final FrequencyMapper frequencyMapper;

    @Autowired
    public CrawlingService(WordMapper wordMapper, FrequencyMapper frequencyMapper) {
        this.wordMapper = wordMapper;
        this.frequencyMapper = frequencyMapper;
    }

    @Transactional
    public List<Long> saveWordList(List<WordSaveRequest> saveWordDtoList) {
        List<Long> savedIdList = new ArrayList<>();
        for (WordSaveRequest request : saveWordDtoList) {
            WordInsertDto insertDto = request.toInsertDto();
            Long existId = wordMapper.findByName(insertDto.getName());
            if(existId == null) {
                wordMapper.saveWord(insertDto);
                existId = insertDto.getId();
            }
            Integer frequency = request.getFrequency();
            frequencyMapper.saveFrequency(frequency, existId);

            savedIdList.add(existId);
        }
        return savedIdList;
    }
}
