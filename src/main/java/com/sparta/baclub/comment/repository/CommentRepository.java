package com.sparta.baclub.comment.repository;

import com.sparta.baclub.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
