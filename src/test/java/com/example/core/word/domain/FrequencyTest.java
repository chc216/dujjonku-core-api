package com.example.core.word.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FrequencyTest {
    @Test
    public void convertToWeeklyNormalListTest() {
        //given
        List<Integer> frequencyList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            frequencyList.add(1);
        }
        Frequency frequency = new Frequency(frequencyList);
        //when
        List<Integer> convertedList = frequency.convertToWeekly(12);

        //then
        List<Integer> weeklyList = List.of(7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7);

        Assertions.assertThat(convertedList).isEqualTo(weeklyList);
    }

    @Test
    public void convertToWeeklySmallListTest() {
        //given
        List<Integer> frequencyList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            frequencyList.add(1);
        }
        Frequency frequency = new Frequency(frequencyList);
        //when
        List<Integer> convertedList = frequency.convertToWeekly(12);

        //then
        List<Integer> weeklyList = List.of
                (
                        6, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0
                );

        Assertions.assertThat(convertedList).isEqualTo(weeklyList);
    }

    @Test
    public void getTodayTest() {
        //given
        List<Integer> freqList = List.of(1);
        Frequency frequency = new Frequency(freqList);
        List<Integer> emptyList = new ArrayList<>();
        Frequency emptyFrequency = new Frequency(emptyList);

        //when
        int today = frequency.getToday();
        int emptyToday = emptyFrequency.getToday();
        //then

        Assertions.assertThat(today).isEqualTo(1);
        Assertions.assertThat(emptyToday).isEqualTo(0);
    }

    @Test
    public void getYesterdayTest() {
        //given
        List<Integer> freqList = List.of(1, 2);
        Frequency frequency = new Frequency(freqList);
        List<Integer> sizeOneList = List.of(1);
        Frequency sizeOneFrequency = new Frequency(sizeOneList);
        List<Integer> emptyList = new ArrayList<>();
        Frequency emptyFrequency = new Frequency(emptyList);

        //when
        int yesterday = frequency.getYesterday();
        int sizeOneYesterday = sizeOneFrequency.getYesterday();
        int emptyYesterday = emptyFrequency.getYesterday();
        //then

        Assertions.assertThat(yesterday).isEqualTo(2);
        Assertions.assertThat(sizeOneYesterday).isEqualTo(0);
        Assertions.assertThat(emptyYesterday).isEqualTo(0);
    }



}