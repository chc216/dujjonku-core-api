package com.example.core.word.service;

import com.example.core.word.infra.mysql.mapper.TodayWordMapper;
import com.example.core.word.infra.mysql.mapper.WordMapper;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodayWordService {
    private final WordMapper wordMapper;
    private final TodayWordMapper todayWordMapper;

    @Transactional
    public void updateTodayWord() {
        //0. 전체를 날린다.
        todayWordMapper.deleteAll();

        //1. 모든 id를 조회한다.
        List<Long> allId = wordMapper.findAllId();

        //2. id를 셔플한다.
        Collections.shuffle(allId);

        //3. 5개의 id를 가져와서 조회 및 저장한다.
        List<Long> randomIdList = allId.stream().limit(5).toList();
        for (Long id : randomIdList) {
            WordInfoMapperDto word = wordMapper.findById(id);
            todayWordMapper.saveTodayWord(word);
        }
    }
    @Transactional(readOnly = true)
    public List<WordInfoMapperDto> getTodayWords() {
        return todayWordMapper.findAll();
    }
}
