package com.sparta.baclub.board.service;

import com.sparta.baclub.board.dto.Request.BoardAddRequestDto;
import com.sparta.baclub.board.dto.Request.BoardDeleteReq;
import com.sparta.baclub.board.dto.Request.BoardUpdateRequestDto;
import com.sparta.baclub.board.dto.Request.FeedBoardReq;
import com.sparta.baclub.board.dto.Response.*;
import com.sparta.baclub.board.entity.BoardEntity;
import com.sparta.baclub.board.errormsg.ResultCode;
import com.sparta.baclub.board.exception.GlobalException;
import com.sparta.baclub.board.repository.BoardRepository;
import com.sparta.baclub.board.validator.BoardValidator;
import com.sparta.baclub.board.validator.UserValidator;
import com.sparta.baclub.comment.dto.CommentResponseDto;
import com.sparta.baclub.comment.entity.Comment;
import com.sparta.baclub.user.entity.User;
import com.sparta.baclub.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static com.sparta.baclub.board.util.BoardUtil.timestampToString;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    //게시글 작성service
    public BoardSaveRes addBoard(BoardAddRequestDto requestDto) {
        User user = getUserEntityByNickname(requestDto.getUserId());
        UserValidator.validator(user);
        return BoardServiceMapper.INSTANCE.toBoardSaveRes(
                boardRepository.save(BoardEntity.builder()
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .visit(0)
                        .userEntity(user)
                        .build()));
    }

    @Transactional
    public BoardGetRes getBoard(Long boardId) {
        BoardEntity board = getBoardEntity(boardId);
        addVisitBoard(board);
        return BoardServiceMapper.INSTANCE.toBoardGetRes(board);
    }

    @Transactional
    public void addVisitBoard(BoardEntity board) {
        board.setVisit(board.getVisit() + 1);
    }

    //게시글 전체 조회 service
    public BoardGetResList getBoards() {
        List<BoardGetRes> boardGetReses = BoardServiceMapper.INSTANCE.toBoardGetReses(
                boardRepository.findAllByOrderByCreateTimestampDesc());

        return BoardGetResList.builder()
                .boardGetReses(boardGetReses)
                .total(boardGetReses.size())
                .build();
    }

    public BoardGetResList getFeedBoards(FeedBoardReq feedBoardReq) {
        List<BoardGetRes> boardGetReses = BoardServiceMapper.INSTANCE.toBoardGetReses(
                boardRepository.findAllUserBoards(feedBoardReq.getUserId()));

        return BoardGetResList.builder()
                .boardGetReses(boardGetReses)
                .total(boardGetReses.size())
                .build();
    }

    //게시글 수정 service
    //인증 과정 빠진 상태입니다.
    public BoardUpdateRes updateBoard(BoardUpdateRequestDto requestDto) {
        BoardEntity prevBoard = boardRepository.findByBoardIdAndUserEntityUserId(
                requestDto.getBoardId(), requestDto.getUserId());
        BoardValidator.validate(prevBoard);

        boardRepository.save(BoardEntity.builder()
                .boardId(prevBoard.getBoardId())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .visit(prevBoard.getVisit())
                .userEntity(prevBoard.getUserEntity())
                .build());
        entityManager.clear();

        BoardEntity board = boardRepository.findByBoardId(requestDto.getBoardId());
        return BoardServiceMapper.INSTANCE.toBoardUpdateRes(board);
    }

    //게시글 삭제 service
    //인증 과정 빠진 상태입니다.
    public BoardDeleteRes deleteBoard(BoardDeleteReq boardDeleteReq) {
        BoardEntity boardEntity = boardRepository.findByBoardIdAndUserEntityUserId(
                boardDeleteReq.getBoardId(), boardDeleteReq.getUserId());
        BoardValidator.validate(boardEntity);
        boardRepository.delete(boardEntity);
        return new BoardDeleteRes();
    }

    // 공통된 부분 메서드 생성
// Id 확인하여 게시판 유무 확인
    private BoardEntity getBoardEntity(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new GlobalException(ResultCode.NOT_EXIST_BOARD));
    }

    // 사용자 엔터티를 닉네임을 통해 가져오기
    private User getUserEntityByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new GlobalException(ResultCode.NOT_EXIST_USER));
    }


    @Mapper
    public interface BoardServiceMapper {

        BoardServiceMapper INSTANCE = Mappers.getMapper(BoardServiceMapper.class);

        @Mapping(source = "createTimestamp", target = "createTimestamp")
        default String toStringTime(Timestamp timestamp) {
            return timestampToString(timestamp);
        }

        @Mapping(source = "user", target = "username")
        default String toUsername(User user) {
            return user.getUsername();
        }

        @Mapping(source = "createTimestamp", target = "createTimestamp")
        @Mapping(source = "user", target = "username")
        BoardSaveRes toBoardSaveRes(BoardEntity board);

        @Mapping(source = "comments", target = "commentReses")
        @Mapping(source = "createTimestamp", target = "createTimestamp")
        @Mapping(source = "user", target = "username")
        BoardUpdateRes toBoardUpdateRes(BoardEntity board);


        @Mapping(source = "comments", target = "commentReses")
        @Mapping(source = "createTimestamp", target = "createTimestamp")
        @Mapping(source = "user", target = "username")
        BoardGetRes toBoardGetRes(BoardEntity board);

        List<BoardGetRes> toBoardGetReses(List<BoardEntity> boardEntities);
    }


}
