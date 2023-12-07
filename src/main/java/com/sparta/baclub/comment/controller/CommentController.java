package com.sparta.baclub.comment.controller;

import com.sparta.baclub.CommonResponseDto;
import com.sparta.baclub.comment.dto.CommentRequestDto;
import com.sparta.baclub.comment.dto.CommentResponseDto;
import com.sparta.baclub.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping ("/{post_id}")
    public ResponseEntity<CommonResponseDto> postComment(@PathVariable Long post_id, @RequestBody CommentRequestDto commentRequestDto) { //로그인 정보 추가
        try {
            CommentResponseDto commentResponseDto = commentService.createComment(post_id, commentRequestDto);
            return ResponseEntity.ok().body(commentResponseDto);
        } catch (IllegalArgumentException e) {
            CommonResponseDto commonResponseDto = new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(commonResponseDto);
        }
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long post_id) {
        List<CommentResponseDto> commentResponseDtoList = commentService.getComments(post_id);
        return ResponseEntity.ok().body(commentResponseDtoList);
    }

    @PatchMapping("/{comment_id}")
    public ResponseEntity<CommonResponseDto> patchComment(@PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto) { //로그인 정보 추가
        try {
            CommentResponseDto commentResponseDto = commentService.updateComment(comment_id, commentRequestDto);
            return ResponseEntity.ok().body(commentResponseDto);
        } catch (IllegalArgumentException e) {
            CommonResponseDto commonResponseDto = new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(commonResponseDto);
        }
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long comment_id) { //로그인 정보 추가
        try {
            commentService.deleteComment(comment_id);
            return ResponseEntity.ok().body(new CommonResponseDto("댓글 삭제가 완료되었습니다.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            CommonResponseDto commonResponseDto = new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(commonResponseDto);
        }
    }


}
