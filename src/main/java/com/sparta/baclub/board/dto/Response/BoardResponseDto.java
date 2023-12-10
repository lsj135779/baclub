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
    private String category;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.nickname = board.getUser().getNickname();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}