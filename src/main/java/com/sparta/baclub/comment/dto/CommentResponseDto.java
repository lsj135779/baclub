package com.sparta.baclub.comment.dto;

import com.sparta.baclub.CommonResponseDto;
import com.sparta.baclub.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto extends CommonResponseDto {
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String userNickname;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.userNickname = comment.getUser().getNickname();
    }

}
