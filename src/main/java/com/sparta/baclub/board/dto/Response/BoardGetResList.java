package com.sparta.baclub.board.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetResList {

    private List<BoardGetRes> boardGetReses;
    private int total;
}