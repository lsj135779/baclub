package com.sparta.baclub.comment.repository;

import com.sparta.baclub.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
   List<Comment> findAllBypostId(Long postId);
}
