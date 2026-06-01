package com.example.core.word.service.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

class RawDataTest {
    @Test
    public void rawDataToStringTest(){
        //given
        RawDataDto rawData1 = new RawDataDto("test", List.of("example1", "example2"), 3);
        RawDataDto rawData2 = new RawDataDto("test", List.of("example1", "example2"), 3);
        List<Object> list = List.of(rawData1, rawData2);
        //when
        String convertedString = list.toString();
        //then
        System.out.println(convertedString);
    }

}