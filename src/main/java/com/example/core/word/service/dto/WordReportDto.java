package com.example.core.word.service.dto;

import com.example.core.word.domain.Frequency;
import com.example.core.word.domain.Word;
import com.example.core.word.infra.mysql.mapper.dto.WordInfoMapperDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//service에서 조립 후 -> controller로 데이터 이동
@Getter
@Builder
@AllArgsConstructor
public class WordReportDto {
    private Long id;
    private String name;
    private String example;
    private String trend;
    private Map<String, Integer> frequency;
    private String meaning;
    private String scenario;

    static public WordReportDto from(WordInfoMapperDto wordInfoMapperDto, List<Integer> freqeuency, String trend) {
        return WordReportDto.builder()
                .id(wordInfoMapperDto.getId())
                .name(wordInfoMapperDto.getName())
                .example(wordInfoMapperDto.getExample())
                .trend(trend).frequency(convertToMap(freqeuency))
                .meaning(wordInfoMapperDto.getMeaning())
                .scenario(wordInfoMapperDto.getScenario())
                .build();
    }

    static public Map<String, Integer> convertToMap(List<Integer> list) {
        Map<String, Integer> converted = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            converted.put("week" + (i + 1), list.get(i));
        }
        return converted;
    }
}
