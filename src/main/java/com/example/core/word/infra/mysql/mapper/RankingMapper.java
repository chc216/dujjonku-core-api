package com.example.core.word.infra.mysql.mapper;

import com.example.core.word.service.dto.RankingDto;
import com.example.core.word.infra.mysql.mapper.dto.RankingInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RankingMapper {
    void insertRankingList(@Param("rankingList") List<RankingInfoDto> rankingList);
    List<RankingInfoDto> findRankingListToday();
}

//랭킹 날짜, 단어 관련 정보(단어이름, 예문, 뜻, 트랜드 지수), 랭킹