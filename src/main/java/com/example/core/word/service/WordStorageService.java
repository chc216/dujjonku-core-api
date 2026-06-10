package com.example.core.word.service;

import com.example.core.word.infra.mysql.mapper.dto.RankingInfoDto;
import com.example.core.word.infra.mysql.mapper.dto.WordInsertDto;
import com.example.core.word.service.dto.RefinedWordDto;
import com.example.core.word.infra.mysql.mapper.FrequencyMapper;
import com.example.core.word.infra.mysql.mapper.WordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WordStorageService {
    private final WordMapper wordMapper;
    private final FrequencyMapper frequencyMapper;

    @Transactional
    public List<Long> saveWordList(List<RefinedWordDto> refinedWordList) {
        List<Long> savedIdList = new ArrayList<>();
        for (RefinedWordDto dto : refinedWordList) {
            WordInsertDto insertDto = toInsertDto(dto);
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

    public WordInsertDto toInsertDto(RefinedWordDto dto){
        return new WordInsertDto(dto.getName(), dto.getMeaning(), dto.getExample(), dto.getScenario());
    }





}
