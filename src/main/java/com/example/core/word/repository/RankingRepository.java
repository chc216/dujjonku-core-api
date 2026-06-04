package com.example.core.word.repository;

import com.example.core.word.service.dto.RankingDto;

import java.util.List;

//추후 레디스 적용을 위해 인터페이스 적용
public interface RankingRepository {
    void update(List<RankingDto> list);
    List<RankingDto> getRankingList();
}
