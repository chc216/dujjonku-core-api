package com.example.core.word.controller;

import com.example.core.word.controller.dto.RankingResponse;
import com.example.core.word.service.RankingService;
import com.example.core.word.service.dto.RankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashboardController {
    private final RankingService rankingService;

    @GetMapping("/ranking")
    public List<RankingResponse> getRanking() {
        List<RankingDto> ranking = rankingService.getRanking();
        List<RankingResponse> reponse = new ArrayList<>();
        for (RankingDto rankingDto : ranking) {
            reponse.add(RankingResponse.from(rankingDto));
        }
        return reponse;
    }
}

