package com.example.core.crawling.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CrawlingMapper {
    List<Long> findTop10IdByFrequency();
    void saveWord();
    Word findById(@Param("id") Long id);

}

//상위 10가지 빈도수 조회 (id조회)
//단어 저장
//단어 조회 (id -> id, 단어, 뜻, 트랜드)