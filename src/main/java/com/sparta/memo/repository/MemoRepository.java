package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();
//    //유저네임 입력하여 조회기능 추가
//    List<Memo> findAllByUsername(String username);
    List<Memo> findAllByContentsContainsOrderByModifiedAtDesc(String keyword);
}