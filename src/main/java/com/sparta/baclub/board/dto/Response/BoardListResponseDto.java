package com.sparta.baclub.board.dto.Response;

import com.sparta.baclub.user.dto.UserInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardListResponseDto {
    private UserInfoDto user;
    private List<BoardResponseDto> boardList;

    public BoardListResponseDto(UserInfoDto user, List<BoardResponseDto> boardList){
        this.user = user;
        this.boardList = boardList;
    }

}