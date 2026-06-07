package com.example.core.word.controller.dto;

import com.example.core.word.service.dto.VoteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteResponse {
    private Long like;
    private Long dislike;

    static public VoteResponse from(VoteDto dto) {
        return new VoteResponse(dto.getLikeCount(), dto.getDislikeCount());
    }
}
