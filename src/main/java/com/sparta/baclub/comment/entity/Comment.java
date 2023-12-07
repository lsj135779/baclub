package com.sparta.baclub.comment.entity;

import com.sparta.baclub.Timestamped;
import com.sparta.baclub.comment.dto.CommentRequestDto;
import com.sparta.baclub.comment.dto.CommentResponseDto;
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
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Post post, CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
        // post 정보 저장하기
        this.post = post;
        // user 정보 저장하기
    }


    public CommentResponseDto update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
        return new CommentResponseDto(this);
    }
}
