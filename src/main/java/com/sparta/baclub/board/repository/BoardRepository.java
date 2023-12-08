package com.sparta.baclub.board.repository;

import com.sparta.baclub.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    //최신생성 기준 게시판 전체 조회
    List<BoardEntity> findAllByOrderByCreateTimestampDesc();

    BoardEntity findByBoardId(Long boardId);

    BoardEntity findByBoardIdAndUserEntityUserId(Long boardId, Long username);

    @Query(value = "select b from BoardEntity b where b.userEntity.username = :username order by b.createTimestamp desc")
    List<BoardEntity> findAllUserBoards(Long username);


}
