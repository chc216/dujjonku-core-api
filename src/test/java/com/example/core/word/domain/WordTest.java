package com.example.core.word.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    @Test
    public void calculateTrendTest() {
        //given
        List<Integer> increasingList = List.of(300, 100);
        Word hotWord = Word.builder().id(0L).name("testWord").meaning("test").frequency(new Frequency(increasingList)).build();
        List<Integer> neutralList = List.of(30,32,30,30);
        Word neutralWord = Word.builder().id(1L).name("testWord").meaning("test").frequency(new Frequency(neutralList)).build();
        List<Integer> decreasingList = List.of(200,1000,10,1);
        Word coldWord = Word.builder().id(2L).name("testWord").meaning("test").frequency(new Frequency(decreasingList)).build();
        //when
        String hotWordTrend = hotWord.calculateTrend();
        String neutralWordTrend = neutralWord.calculateTrend();
        String coldWordTrend = coldWord.calculateTrend();
        //then
        Assertions.assertThat(hotWordTrend).isEqualTo("hot");
        Assertions.assertThat(neutralWordTrend).isEqualTo("neutral");
        Assertions.assertThat(coldWordTrend).isEqualTo("cold");
    }

}