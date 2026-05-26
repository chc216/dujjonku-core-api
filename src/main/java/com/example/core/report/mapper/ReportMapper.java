package com.example.core.report.mapper;

import com.example.core.report.dto.WordDailyFrequencyDto;
import com.example.core.report.dto.WordInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//리포트에서 필요한 기능 => id를 받아서 조회하기 -> id를 넘겨서 id에 대한 정보를 가져온다. 2. id를 넘겨서 id관련 빈도수 데이터를 모두 가져온다.(84일치를 가져온다.)
@Mapper
public interface ReportMapper {
    WordInfoDto findById(@Param("wordId") Long wordId);
    //db에서 조회할때 빈도수가 없는 날에는 자동으로 0이 들어옴
    //오늘로부터 84일 전까지(12주) 빈도수 데이터를 조회한다.
    List<WordDailyFrequencyDto> findFrequenciesList(@Param("wordId") Long wordId, @Param("dayCount") Integer dayCount);
}