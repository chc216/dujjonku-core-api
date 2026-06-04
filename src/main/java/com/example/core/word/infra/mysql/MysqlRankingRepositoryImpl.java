package com.example.core.word.infra.mysql;

import com.example.core.word.infra.mysql.mapper.RankingMapper;
import com.example.core.word.infra.mysql.mapper.dto.RankingInfoDto;
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
        List<RankingInfoDto> insertList = list.stream().map(this::toInsert).toList();
        rankingMapper.insertRankingList(insertList);
    }

    @Override
    public List<RankingDto> getRankingList() {
        List<RankingInfoDto> list = rankingMapper.findRankingListToday();
        return list.stream().map(this::toRankingDto).toList();
    }

    private RankingInfoDto toInsert(RankingDto ranking) {
        return new RankingInfoDto(ranking.getWordId(), ranking.getName(), ranking.getMeaning(), ranking.getExample(), ranking.getRank(), ranking.getTrend());
    }

    private RankingDto toRankingDto(RankingInfoDto infoDto) {
        return new RankingDto(infoDto.getWordId(), infoDto.getName(), infoDto.getMeaning(), infoDto.getExample(), infoDto.getRank(), infoDto.getTrend());
    }

}
