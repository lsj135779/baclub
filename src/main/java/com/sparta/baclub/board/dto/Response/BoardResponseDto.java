package com.sparta.baclub.board.dto.Response;

import com.sparta.baclub.CommonResponseDto;
import com.sparta.baclub.board.entity.Board;
import com.sparta.baclub.user.dto.UserInfoDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class BoardResponseDto extends CommonResponseDto {
    private Long id;
    private String title;
    private String content;
    private Boolean isCompleted;
    private UserInfoDto user;
    private LocalDateTime createDate;

    public BoardResponseDto(Board board) {
        super("Success", HttpStatus.OK.value()); // CommonResponseDto의 생성자 호출
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.isCompleted = board.getIsCompleted();
        this.user = new UserInfoDto(board.getUser());
        this.createDate = board.getCreateDate();


    }
}