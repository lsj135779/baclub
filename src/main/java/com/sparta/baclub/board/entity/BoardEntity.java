package com.sparta.baclub.board.entity;

import com.sparta.baclub.comment.entity.Comment;
import com.sparta.baclub.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    private String title;
    private String content;
    private int visit;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userEntity;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL)
    @OrderBy("createTimestamp DESC")
    private List<Comment> commentEntities;
}