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
    @Value("${google.gemini.api-key}")
    String apiKey;


    @Override
    public List<RefinedWordDto> refine(List<RawDataDto> list) {

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (RawDataDto rawDataDto : list) {
            frequencyMap.put(rawDataDto.getName(), rawDataDto.getFrequency());
        }

        Client client = Client.builder().apiKey(apiKey).build();
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
                
                [필터링 제외 기준]
                1. 단순 오타나 자음/모음 남발 (예: ㅋㅋㅋ, ㅠㅠㅠ, ㅏㅏㅏ)
                2. 의미를 알 수 없는 무작위 알파벳이나 특수문자 조합
                3. 이미 국어사전에 등재된 너무 평범한 일반 명사
                
                [추출 기준]
                1. 특정 커뮤니티, SNS, 방송 등에서 최근 쓰이기 시작한 단어
                2. 기존 단어지만 완전히 새로운 뜻으로 쓰이는 단어 (밈)
                
                반드시 제공된 JSON 형식에 맞춰서 답변을 반환해.
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
                "        \"meaning\": \"해당 유행어의 뜻\",\n" +
                "        \"example\": \"실제 사용 예시 문장\"\n" +
                "      }\n" +
                "    ]" +
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
