package com.example.core.crawling.controller;


import org.springframework.web.bind.annotation.RestController;

//핵심 -> 1. 제미나이 통신 2. 데이터 저장(단어 관련) 3. 모든 단어 빈도수 업데이트 + 모든 단어 트랜드 계산 및 업데이트 4. 최근 빈도 top 10 레디스 저장
//1. 제미나이한테 json을 보낸다.
//2. 응답을 받아서 객체화한다.
//3. 객체화된 데이터들의 전날 빈도수를 조회한다.
//4. 모든 각 객체의 트랜드를 계산한다.
//5. 모든 객체를 저장한다.
//6. 상위 10가지 빈도수의 id를 가져와서 각 단어를 조회한다.
//7. 조회된 단어를 레디스에 저장한다.
@RestController
public class CrawlingController {
}


//redis에 저장