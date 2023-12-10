package com.sparta.baclub.board.service;

import com.sparta.baclub.board.dto.Request.BoardRequestDto;
import com.sparta.baclub.board.dto.Response.BoardListResponseDto;
import com.sparta.baclub.board.dto.Response.BoardResponseDto;
import com.sparta.baclub.board.entity.Board;
import com.sparta.baclub.board.repository.BoardRepository;
import com.sparta.baclub.comment.repository.CommentRepository;
import com.sparta.baclub.user.dto.UserInfoDto;
import com.sparta.baclub.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public BoardResponseDto createBoard(BoardRequestDto dto, User user){
        Board board = new Board(dto);
        board.setUser(user);
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto getBoardDto(Long boardId) {
        Board board = getBoard(boardId);
        return new BoardResponseDto(board);
    }

    public Map<UserInfoDto, List<BoardResponseDto>> getUserBoardMap() {
        Map<UserInfoDto, List<BoardResponseDto>> userBoardMap = new HashMap<>();

        List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // 작성일 기준 내림차순

        boardList.stream().forEach(board -> {
            var userInfoDto = new UserInfoDto(board.getUser());
            var boardDto = new BoardResponseDto(board);
            if (userBoardMap.containsKey(userInfoDto)) {
                // 유저 게시글목록에 항목 추가
                userBoardMap.get(userInfoDto).add(boardDto);

            } else {
                // 유저 게시글목록 새로 추가
                userBoardMap.put(userInfoDto, new ArrayList<>(List.of(boardDto)));
            }
        });

        return userBoardMap;
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto, User user) {
        Board board = getUserBoard(boardId, user);

        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());

        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto completeBoard(Long boardId, User user) {
        Board board = getUserBoard(boardId, user);

        board.complete(); // 완료 처리

        return new BoardResponseDto(board);
    }

    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 ID입니다."));
    }

    public Board getUserBoard(Long boardId, User user) {
        Board board = getBoard(boardId);

        if(!user.getId().equals(board.getUser().getId())){
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return board;
    }

    public void deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (!board.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("게시글 작성자만 수정 가능합니다");
        }

        boardRepository.delete(board);
    }


}

