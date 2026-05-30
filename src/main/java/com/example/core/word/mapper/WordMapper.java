package com.example.core.word.mapper;

import com.example.core.word.domain.Word;
import com.example.core.word.reportdto.WordInfoMapperDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//리포트에서 필요한 기능 => id를 받아서 조회하기 -> id를 넘겨서 id에 대한 정보를 가져온다. 2. id를 넘겨서 id관련 빈도수 데이터를 모두 가져온다.(84일치를 가져온다.)
@Mapper
public interface WordMapper {
    WordInfoMapperDto findById(@Param("id") Long id);

}