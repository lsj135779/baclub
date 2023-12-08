package com.sparta.baclub.board.dto.Response;

import com.sparta.baclub.comment.dto.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetRes {

    private Long boardId;
    private String title;
    private String content;
    private String createTimestamp;
    private String username;
    private int visit;
    private List<CommentResponseDto> commentRes;
}