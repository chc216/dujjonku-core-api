package com.example.core.report.service;

import com.example.core.report.dto.ReportResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ReportServiceTest {
    @Autowired
    private ReportService reportService;
    @Test
    public void testGetReport() {
        ReportResponseDto wordReport = reportService.getWordReport("1");
        System.out.println(wordReport.getWord());
        System.out.println(wordReport.getExample());
        System.out.println(wordReport.getFrequency());
    }

}