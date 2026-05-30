package com.example.core.word.reportdto;

import com.example.core.word.domain.Word;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//service에서 조립 후 -> controller로 데이터 이동
@Getter
public class ReportResponseDto {
    private Long id;
    private String name;
    private String example;
    private String trend;
    private Map<String, Integer> frequency;

    public ReportResponseDto(Word word, String trend, List<Integer> weeklyFrequency) {
        this.id = word.getId();
        this.name = word.getName();
        this.example = word.getExample();;
        this.trend = trend;
        this.frequency = convertWeeklyFrequency(weeklyFrequency);
    }

    private Map<String, Integer> convertWeeklyFrequency(List<Integer> list) {
        Map<String, Integer> converted = new LinkedHashMap<>();
        for(int i = 0; i < list.size(); i++) {
            converted.put("week" + (i+1), list.get(i));
        }
        return converted;
    }


}
