package com.sparta.baclub.comment.entity;

import com.sparta.baclub.Timestamped;
import com.sparta.baclub.comment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

        // post, user 관계설정하기

    public Comment(Post post, CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
        // post 정보 저장하기
        // user 정보 저장하기
    }




}
