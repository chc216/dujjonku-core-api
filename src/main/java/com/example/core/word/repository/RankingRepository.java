package com.example.core.word.repository;

import com.example.core.word.service.dto.RankingDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RankingRepository {
    void update(List<RankingDto> list);
    List<RankingDto> getRankingList();
}
