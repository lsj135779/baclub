package com.sparta.baclub.board.dto.Request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDeleteReq {

    private Long userId;
    private Long boardId;
}