package com.example.core.word.infra.mysql.mapper;

import com.example.core.word.infra.mysql.mapper.dto.TodayWordDto;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TodayWordMapper {
    void saveTodayWord(WordInfoMapperDto dto);
    void deleteAll();
    List<TodayWordDto> findAll();
}
