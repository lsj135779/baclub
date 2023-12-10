package com.sparta.baclub.board.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {

    private String title;
    private String content;
    private String userId;
}
