package com.example.core.word.service;

import com.example.core.word.service.dto.RawDataDto;
import com.example.core.word.service.dto.RefinedWordDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class GeminiAiRefinerImplTest {
    @Autowired
    AiRefiner aiRefiner;

    @Test
    public void refineTest() {
        //given
        RawDataDto rawData1 = new RawDataDto("두쫀쿠", List.of("오늘 점심 먹고 두쫀쿠 먹었는데 극락 감 ㅠㅠ",
                "말차는 이제 한물갔지, 요즘은 두쫀쿠가 대세임",
                "아 두쫀쿠 품절이네 화난다..."), 200);
        RawDataDto rawData2 = new RawDataDto("테스트", List.of("테스트를 테스트해서 테스트 할거에요", "아~~ 테스트 마렵네 오늘?", "야 너 왤케 테스트스러워 오늘"), 30000);
        List<RawDataDto> testDataList = List.of(rawData1, rawData2);
        
        //when
        List<RefinedWordDto> refinedData = aiRefiner.refine(testDataList);

        //then
        Assertions.assertThat(refinedData.size()).isEqualTo(1);
        Assertions.assertThat(refinedData.get(0).getName()).isEqualTo("두쫀쿠");
    }

}