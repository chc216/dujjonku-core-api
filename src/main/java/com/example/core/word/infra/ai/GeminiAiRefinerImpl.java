package com.example.core.word.infra.ai;

import com.example.core.word.service.AiRefiner;
import com.example.core.word.service.dto.RefinedWordDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import com.example.core.word.service.dto.RawDataDto;
import com.google.genai.Client;
import com.google.genai.types.*;
import com.google.genai.types.ThinkingLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiAiRefinerImpl implements AiRefiner {
    private final ObjectMapper objectMapper;
    private final Client client;


    @Override
    public List<RefinedWordDto> refine(List<RawDataDto> list) {

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (RawDataDto rawDataDto : list) {
            frequencyMap.put(rawDataDto.getName(), rawDataDto.getFrequency());
        }

        ImmutableList<SafetySetting> safetySettings = ImmutableList.of(
                SafetySetting.builder()
                        .category(HarmCategory.Known.HARM_CATEGORY_HATE_SPEECH) // 혐오 발언
                        .threshold(HarmBlockThreshold.Known.BLOCK_ONLY_HIGH)
                        .build(),
                SafetySetting.builder()
                        .category(HarmCategory.Known.HARM_CATEGORY_HARASSMENT) // 괴롭힘 (인터넷 밈에서 자주 걸림)
                        .threshold(HarmBlockThreshold.Known.BLOCK_ONLY_HIGH)
                        .build(),
                SafetySetting.builder()
                        .category(HarmCategory.Known.HARM_CATEGORY_SEXUALLY_EXPLICIT) // 성적 콘텐츠
                        .threshold(HarmBlockThreshold.Known.BLOCK_ONLY_HIGH)
                        .build(),
                SafetySetting.builder()
                        .category(HarmCategory.Known.HARM_CATEGORY_DANGEROUS_CONTENT) // 위험 콘텐츠
                        .threshold(HarmBlockThreshold.Known.BLOCK_ONLY_HIGH)
                        .build()
        );
        String promptText = """
                너는 한국어 트렌드 및 신조어 분석 전문가야.
                제공된 단어 목록을 분석하여 '실제 사람들이 의미를 가지고 쓰는 신조어/유행어'만 추출해.
                
                [중요] 예시 문장의 사용법:
                - 예시 문장은 단어가 '어떤 의미로' 쓰이는지 파악하는 용도로만 사용해.
                - 예시 문장이 자연스럽다는 이유만으로 유행어로 판단하지 마.
                - 판단 기준은 "이 단어 자체가 신조어/줄임말/밈으로서의 형태적·의미적 특징을 가지는가"야.
                
                [유행어로 인정하는 특징]
                1. 줄임말 형태 (예: 점메추, 분좋카, 알잘딱깔센)
                2. 기존 단어를 새로운 뜻으로 비트는 밈 (예: 럭키비키, 너 T야)
                3. 합성·변형으로 만들어진 신조어 (예: 갓생, 스불재)
                
                [필터링 제외 기준]
                1. 자음/모음 남발 (ㅋㅋㅋ, ㅠㅠㅠ)
                2. 무작위 알파벳/특수문자 조합
                3. '테스트', '학교', '점심'처럼 형태 변형 없이 그냥 일반적으로 쓰이는 평범한 단어
                   → 예문이 아무리 자연스러워도 단어 자체에 신조어적 특징이 없으면 제외
                
                [상황 시나리오(scenario) 작성 규칙]
                - 이 유행어를 쓰기 딱 좋은 일상 상황을 한 문장으로 묘사해.
                - "이런 날 쓰세요:" 뒤에 올 법한, 장면이 그려지는 구체적인 상황 한 줄.
                - 설명조("~할 때 쓴다")가 아니라 한 장면을 보여줘. 예) 알람 끄고 다시 잤다가 지각한 아침
                - 단어 자체나 그 뜻을 문장에 넣지 마. 오직 '상황'만 묘사해. (예시 문장 example과 겹치면 안 됨)
                
                **반드시 제공된 JSON 형식에 맞춰서 답변을 반환해.**
                """;

        Content systemInstruction = Content.fromParts(Part.fromText(promptText));

        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseMimeType("application/json")
                .thinkingConfig(ThinkingConfig.builder().thinkingLevel(new ThinkingLevel("medium")))
                .safetySettings(safetySettings)
                .systemInstruction(systemInstruction)
                .build();

        String userMessage = "다음 단어 목록을 분석해줘:\n결과는 반드시 아래 JSON 배열 형식으로만 출력해:\n" +
                "    [\n" +
                "      {\n" +
                "        \"name\": \"추출된 유행어\",\n" +
                "        \"meaning\": \"해당 유행어의 뜻. 단, 한줄 이내로 간략하게 해당 단어를 설명할 것\",\n" +
                "        \"example\": \"실제 사용 예시 문장\",\n" +
                "        \"scenario\": \"이 유행어를 쓰기 딱 좋은 상황 한 줄 (단어/뜻은 넣지 말 것)\"\n" +
                "      }\n" +
                "    ]\n" +
                list.toString();

        GenerateContentResponse response =
                client.models.generateContent("gemini-3.1-flash-lite", userMessage, config);

        String jsonResult = response.text();
        List<RefinedWordDto> refinedWords;
        try {
            refinedWords = objectMapper.readValue(jsonResult, new TypeReference<List<RefinedWordDto>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("ai json response를 refinedDto로 변환하는 중 에러 발생", e);
        }

        for (RefinedWordDto refinedWord : refinedWords) {
            refinedWord.setFrequency(frequencyMap.get(refinedWord.getName()));
        }

        return refinedWords;


    }
}
