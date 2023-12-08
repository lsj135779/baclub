package com.sparta.baclub.board.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveRes {

    private Long boardId;
    private String title;
    private String content;
    private String createTimestamp;
    private String username;
}