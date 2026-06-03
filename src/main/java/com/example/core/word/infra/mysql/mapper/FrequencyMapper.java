package com.example.core.word.infra.mysql.mapper;

import com.example.core.word.infra.mysql.mapper.dto.WordFrequencyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FrequencyMapper {
    List<Integer> findFrequencyListByWordId(@Param("size") Integer size, @Param("id") Long id);
    void saveFrequency(@Param("frequency") Integer frequency, @Param("id") Long id);
    List<WordFrequencyDto> findTodayFrequencyListAscending(@Param("size") Integer size);
    Integer findYesterdayFrequencyById(@Param("id") Long id);
}