package com.example.core.report.service;

import com.example.core.report.dto.ReportResponseDto;
import com.example.core.report.dto.WordDailyFrequencyDto;
import com.example.core.report.dto.WordInfoDto;
import com.example.core.report.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {
    private final ReportMapper reportMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    public ReportResponseDto getWordReport(String wordId) {
        Long id = Long.valueOf(wordId);
        WordInfoDto wordInfoDto = reportMapper.findById(id);

        List<WordDailyFrequencyDto> dailyList = reportMapper.findFrequenciesList(id, 84);
        Map<String, Integer> weeklyList = convertDailyListToWeekly(dailyList);

        return new ReportResponseDto(wordInfoDto, weeklyList);
    }

    private Map<String, Integer> convertDailyListToWeekly(List<WordDailyFrequencyDto> list) {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            result.put("week" + i + "ago", 0);
        }

        for (int i = 0; i < list.size(); i++) {
            int week = i / 7 + 1;
            String key = "week" + week + "ago";
            int tmp = result.get(key);
            tmp += list.get(i).getFrequency();
            result.put(key, tmp);
        }
        return result;
    }


}
