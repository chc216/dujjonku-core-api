package com.example.core.report.controller;

import com.example.core.report.dto.ReportResponseDto;
import com.example.core.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    private final ReportService service;

    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/report/{id}")
    public ReportResponseDto report(@PathVariable("id") String id) {
        System.out.println("id = " + id);
        return service.getWordReport(id);
    }
}
