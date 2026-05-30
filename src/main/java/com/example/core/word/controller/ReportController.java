package com.example.core.word.controller;

import com.example.core.word.reportdto.ReportResponseDto;
import com.example.core.word.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


//report 도메인 관련 컨트롤러
@RestController
public class ReportController {
    private final ReportService service;

    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/report/{id}")
    public ReportResponseDto report(@PathVariable("id") String id) {
        return service.getWordReport(id);
    }
}
