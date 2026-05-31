package com.example.core.word.service;

import com.example.core.word.crawlingdto.WordSaveRequest;
import com.example.core.word.mapper.FrequencyMapper;
import com.example.core.word.mapper.WordMapper;
import com.example.core.word.reportdto.WordInfoMapperDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



//보통 테스트할때는 각 엣지케이스별로 테스트 메서드를 만들어야 어디서 오류가 발생하는지 알 수 있음.
//그리고 동시에 해당 메서드에 대한 문서화 기능도 제공할 수 있다. -> @Display를 이용하여 해당 메서드의 입력을 넣으면 어떻게 반환하는지를 적는다. (이상적인 시나리오를 적는다.)
@SpringBootTest
@Transactional
class CrawlingServiceTest {
    @Autowired
    CrawlingService crawlingService;
    @Autowired
    WordMapper wordMapper;
    @Autowired
    FrequencyMapper frequencyMapper;

    @Test
    @DisplayName("존재하지 않는 단어만 저장한다. 단어와 빈도 모두 업데이트 되어야한다.")
    public void saveNoneExistWords() {
        //given
        WordSaveRequest request1 = new WordSaveRequest("단어1", "의미1", "예제1", 3000);
        WordSaveRequest request2 = new WordSaveRequest("단어2", "의미2", "예제2", 3000);
        WordSaveRequest request3 = new WordSaveRequest("단어3", "의미3", "예제3", 3000);
        WordSaveRequest request4 = new WordSaveRequest("단어4", "의미4", "예제4", 3000);
        List<WordSaveRequest> saveList = List.of(request1, request2, request3, request4);
        //when
        List<Long> savedId = crawlingService.saveWordList(saveList);

        //then
        Assertions.assertThat(savedId.size()).isEqualTo(saveList.size());
        for(int i = 0; i < saveList.size(); i++) {
            Long id = savedId.get(i);
            WordSaveRequest request = saveList.get(i);

            //각 단어 테이블 저장 잘 됐는지 테스트
            WordInfoMapperDto findWord = wordMapper.findById(id);
            Assertions.assertThat(findWord).isNotNull();
            Assertions.assertThat(findWord.getName()).isEqualTo(request.getName());
            Assertions.assertThat(findWord.getMeaning()).isEqualTo(request.getMeaning());
            Assertions.assertThat(findWord.getExample()).isEqualTo(request.getExample());

            //각 빈도 테이블에 저장 잘 됐는지 테스트
            List<Integer> frequency = frequencyMapper.getFrequencyList(1, id);
            Assertions.assertThat(frequency.get(0)).isEqualTo(request.getFrequency());
        }

    }

    @Test
    @DisplayName("이미 존재하는 단어만 저장한다. 빈도만 업데이트되어야한다.")
    public void saveOnlyExistWords() {

    }

}