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
        Frequency hot = new Frequency(increasingList);
        List<Integer> neutralList = List.of(30,32,30,30);
        Frequency neutral = new Frequency(neutralList);
        List<Integer> decreasingList = List.of(200,1000,10,1);
        Frequency cold = new Frequency(decreasingList);
        //when
        String hotWordTrend = hot.calculateTrend();
        String neutralWordTrend = neutral.calculateTrend();
        String coldWordTrend = cold.calculateTrend();
        //then
        Assertions.assertThat(hotWordTrend).isEqualTo("hot");
        Assertions.assertThat(neutralWordTrend).isEqualTo("neutral");
        Assertions.assertThat(coldWordTrend).isEqualTo("cold");
    }

}