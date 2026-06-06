package com.example.core.word.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteDto {
    private Long likeCount;
    private Long dislikeCount;
}
