package com.example.core.word.controller;

import com.example.core.word.controller.dto.CountResponse;
import com.example.core.word.controller.dto.VoteResponse;
import com.example.core.word.service.dto.VoteDto;
import com.example.core.word.service.dto.WordReportDto;
import com.example.core.word.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


//report 도메인 관련 컨트롤러
@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;

    @GetMapping("/report/{id}")
    public WordReportDto report(@PathVariable String id) {
        return service.getWordReport(Long.valueOf(id));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<CountResponse> like(@PathVariable String id) {
        Long likeCount= service.increaseLike(Long.valueOf(id));
        return ResponseEntity.ok(new CountResponse(likeCount));
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<CountResponse> dislike(@PathVariable String id) {
        Long dislikeCount= service.increaseDislike(Long.valueOf(id));
        return ResponseEntity.ok(new CountResponse(dislikeCount));
    }

    @GetMapping("/{id}/vote")
    public ResponseEntity<VoteResponse> getVoteCount(@PathVariable String id) {
        VoteDto voteDto = service.getVoteById(Long.valueOf(id));
        return ResponseEntity.ok(VoteResponse.from(voteDto));
    }
}
