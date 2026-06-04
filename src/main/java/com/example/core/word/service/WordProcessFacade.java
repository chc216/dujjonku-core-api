package com.example.core.word.service;


import com.example.core.word.service.dto.RawDataDto;
import com.example.core.word.service.dto.RefinedWordDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordProcessFacade {
    private final WordStorageService wordStorageService;
    private final AiRefiner aiRefiner;
    private final RankingService rankingService;


    @Transactional //나중에 수정해야함 ai api때문에 디비를 계속 잡고 있게 되는데 어차피 하루에 한번만 실행되는거니까 나중에 리팩토링할 것
    //하나의 서비스로 처리하기에는 1. ai관련 서비스 2. 단어 저장 서비스 3. 랭킹 관련 도메인을 모두 하나의 서비스에서 처리해야했음 -> 따라서 파사드 패턴으로 묶는다
    public void process(List<RawDataDto> rawDataList) {
        List<RefinedWordDto> refinedWordDtoList = aiRefiner.refine(rawDataList);
        wordStorageService.saveWordList(refinedWordDtoList);
        rankingService.updateRanking();
    }


    //나중에 할 것
    //ai기능까지 트랜잭션으로 묶어 버리면 ai통신이 끝날 때 까지 디비 커넥션을 잡고 있음 트랜잭션을 나눠야함
    public void saveAndUpdateRanking(List<RefinedWordDto> refinedWordDtoList) {
        wordStorageService.saveWordList(refinedWordDtoList);
        rankingService.updateRanking();

    }
}
