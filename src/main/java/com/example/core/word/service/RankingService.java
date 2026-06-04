package com.example.core.word.service;


import com.example.core.word.domain.Frequency;
import com.example.core.word.infra.mysql.mapper.FrequencyMapper;
import com.example.core.word.infra.mysql.mapper.WordMapper;
import com.example.core.word.service.dto.RankingDto;
import com.example.core.word.infra.mysql.mapper.dto.RankingInfoDto;
import com.example.core.word.infra.mysql.mapper.dto.WordFrequencyDto;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import com.example.core.word.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RankingService {
    private final RankingRepository rankingRepository;
    private final FrequencyMapper frequencyMapper;
    private final WordMapper wordMapper;


    public void updateRanking() {
        //1. 데이터를 가져와서 도메인 객체 조립

        //복잡한 로직이 아니기 때문에 매퍼를 직접 사용하고, 매퍼의 dto에 의존하는건 실용성을 위해 넘어간다.
        //베스트는 리포지토리에서 1. dto변환의 책임 2. 인프라 의존성을 리포지토리에서 제거하는 것이다.
        List<WordFrequencyDto> todayFrequencyListAscending = frequencyMapper.findTodayFrequencyListAscending(20);
        List<RankingDto> rankingList = new ArrayList<>();
        for(int rank = 0; rank < todayFrequencyListAscending.size(); rank++){
            Integer today = todayFrequencyListAscending.get(rank).getFrequency();
            Long id = todayFrequencyListAscending.get(rank).getWordId();
            //N+1문제 발생
            Integer yesterday = frequencyMapper.findYesterdayFrequencyById(id);
            if (yesterday == null) {
                yesterday = 0;
            }
            WordInfoMapperDto wordInfoMapperDto = wordMapper.findById(id);
            Frequency frequency = new Frequency(List.of(today, yesterday));
            String trend = frequency.calculateTrend();

            rankingList.add(
                    new RankingDto(wordInfoMapperDto.getId(), wordInfoMapperDto.getName(), wordInfoMapperDto.getMeaning(), wordInfoMapperDto.getExample(), rank, trend)
            );



        }

        //2. 추상화된 리포지토리를 이용하여 업데이트 (추후 mysql -> redis로 바꿀 예정이기 때문에)
        //일단 여기서만이라도 서비스 dto를 이용하여 의존성을 최대한 제거한다.
        rankingRepository.update(rankingList);
    }

    public List<RankingDto> getRanking() {
        return rankingRepository.getRankingList();
    }
}
