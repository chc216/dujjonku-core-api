package com.example.core.word.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FrequencyMapper {
    List<Integer> getFrequencyList(@Param("count") Integer count, @Param("id") Long id);

}