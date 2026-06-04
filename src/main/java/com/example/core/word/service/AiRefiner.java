package com.example.core.word.service;

import com.example.core.word.service.dto.RawDataDto;
import com.example.core.word.service.dto.RefinedWordDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AiRefiner {
    List<RefinedWordDto> refine(List<RawDataDto> list);
}
