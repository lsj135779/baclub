package com.sparta.baclub.board.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardAddRequestDto {

    //받는 내용
    private String title;
    private String content;
    private String userId;
}