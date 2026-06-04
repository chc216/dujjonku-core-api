package com.example.core.word.service;

import com.example.core.word.infra.mysql.mapper.TodayWordMapper;
import com.example.core.word.infra.mysql.mapper.WordMapper;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import com.example.core.word.infra.mysql.mapper.dto.WordInsertDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class TodayWordServiceTest {
    @Autowired
    TodayWordService todayWordService;
    @Autowired
    WordMapper wordMapper;

    @Autowired
    TodayWordMapper todayWordMapper;


    @BeforeEach
    void setUp() {
        wordMapper.deleteAll();
        for (int i = 1; i <= 10; i++) {
            wordMapper.saveWord(new WordInsertDto("test" + i, "meaning" + i, "example" + i));
        }
    }

    @Test
    public void 오늘의단어_5개가_저장된다() {
        //when
        todayWordService.updateTodayWord();

        //then
        List<WordInfoMapperDto> findTodayWord = todayWordMapper.findAll();
        for (WordInfoMapperDto wordInfoMapperDto : findTodayWord) {
            System.out.println(wordInfoMapperDto.getId());
            System.out.println(wordInfoMapperDto.getName());
        }

    }




}