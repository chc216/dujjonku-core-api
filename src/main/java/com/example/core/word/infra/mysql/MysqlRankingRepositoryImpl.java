package com.example.core.word.infra.mysql;

import com.example.core.word.infra.mysql.mapper.RankingMapper;
import com.example.core.word.service.dto.RankingDto;
import com.example.core.word.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MysqlRankingRepositoryImpl implements RankingRepository {
    private final RankingMapper rankingMapper;
    @Override
    public void update(List<RankingDto> list) {
        rankingMapper.insertRankingList(list);
    }

    @Override
    public List<RankingDto> getRankingList() {
        return rankingMapper.findRankingListToday();
    }
}
