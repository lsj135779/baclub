package com.sparta.baclub.board.controller;

import com.sparta.baclub.board.dto.Request.BoardAddRequestDto;
import com.sparta.baclub.board.dto.Request.BoardDeleteReq;
import com.sparta.baclub.board.dto.Request.BoardUpdateRequestDto;
import com.sparta.baclub.board.dto.Request.FeedBoardReq;
import com.sparta.baclub.board.dto.Response.*;
import com.sparta.baclub.board.service.BoardService;
import com.sparta.baclub.user.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards")
public class BoardController {

    private final BoardService boardService;

    //포스트 작성
    @PostMapping
    public RestResponse<BoardSaveRes> addBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BoardAddRequestDto requestDto) {
        requestDto.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(boardService.addBoard(requestDto));
    }

    //포스트 단일 조회
    @GetMapping("/{boardId}")
    public RestResponse<BoardGetRes> getBoard(@PathVariable Long boardId) {
        return RestResponse.success(boardService.getBoard(boardId));
    }

    //포스트 전체 조회
    @GetMapping
    public RestResponse<BoardGetResList> getBoards() {
        return RestResponse.success(boardService.getBoards());
    }

    @GetMapping("/feed")
    public RestResponse<BoardGetResList> getFeedBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FeedBoardReq feedBoardReq) {
        feedBoardReq.setUserId(userDetails.getUser().getUsername());
        return RestResponse.success(boardService.getFeedBoards(feedBoardReq));
    }

    //포스트 수정
    @PatchMapping
    public RestResponse<BoardUpdateRes> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BoardUpdateRequestDto requestDto) {
        requestDto.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(boardService.updateBoard(requestDto));
    }

    //포스트 삭제
    @DeleteMapping
    public RestResponse<BoardDeleteRes> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BoardDeleteReq boardDeleteReq) {
        boardDeleteReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(boardService.deleteBoard(boardDeleteReq));
    }
}