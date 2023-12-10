package com.sparta.baclub.comment.service;

import com.sparta.baclub.board.entity.Board;
import com.sparta.baclub.board.repository.BoardRepository;
import com.sparta.baclub.comment.dto.CommentRequestDto;
import com.sparta.baclub.comment.dto.CommentResponseDto;
import com.sparta.baclub.comment.entity.Comment;
import com.sparta.baclub.comment.repository.CommentRepository;
import com.sparta.baclub.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        // 게시글이 있는지 postId 확인하기
        Board board = boardRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 요청한 정보에 content가 없는 경우
        if (commentRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }

        Comment comment = new Comment(board, commentRequestDto, user);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }


    public List<CommentResponseDto> getComments(Long postId) {
//        List<Comment> commentList = commentRepository.findAllBypostId(postId); // 댓글DB에서 postId로 가져오는 거 테스트
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

//        commentList.forEach(comment -> commentResponseDtoList.add(new CommentResponseDto(comment)));

        return commentResponseDtoList;
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        // 로그인한 유저가 댓글을 작성한 유저와 같은지 검사
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("댓글 작성자만 수정 가능합니다");
        }

        CommentResponseDto commentResponseDto = comment.update(commentRequestDto);
        return commentResponseDto;
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        // 로그인한 유저가 댓글을 작성한 유저와 같은지 검사
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("댓글 작성자만 수정 가능합니다");
        }

        commentRepository.delete(comment);
    }
}
