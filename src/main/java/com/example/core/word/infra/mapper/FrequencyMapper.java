package com.example.core.word.infra.mapper;

import com.example.core.word.domain.Frequency;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FrequencyMapper {
    List<Integer> getFrequencyList(@Param("count") Integer count, @Param("id") Long id);
    void saveFrequency(@Param("frequency") Integer frequency, @Param("id") Long id);
}