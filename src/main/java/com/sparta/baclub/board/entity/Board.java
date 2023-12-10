package com.sparta.baclub.board.entity;

import com.sparta.baclub.Timestamped;
import com.sparta.baclub.board.dto.Request.BoardRequestDto;
import com.sparta.baclub.board.dto.Response.BoardResponseDto;
import com.sparta.baclub.comment.entity.Comment;
import com.sparta.baclub.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String category;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments;

    public Board(BoardRequestDto dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.category = dto.getCategory();
    }
    //연관관계 메서드
    public void setUser(User user){
        this.user = user;
    }

    // 서비스 메서드
    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }
}
