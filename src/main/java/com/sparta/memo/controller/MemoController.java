package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto){
        //RequestDto - > Entitu로 저장
        Memo memo = new Memo(requestDto);

        //Memo Max ID 찾기
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) +1 : 1;
        memo.setId(maxId);

        //DB에 저장
        memoList.put(memo.getId(), memo);

        //Entity -> ResponseDto로 변환
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }
    //전체 목록조회
    @GetMapping("/memos")
    public List<MemoResponseDto> getmemos(){
        // Map to List
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;
    }
    //수정 기능
    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        //해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)){
            Memo memo = memoList.get(id);
            //memo수정하기
            memo.update(requestDto);
            return memo.getId();
        }else{
            throw new IllegalArgumentException("선택한 메모는 존재하지않습니다.");
        }
    }
    //삭제 기능구현
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id){
        //해당 메모가 존재하는지 확인
        if(memoList.containsKey(id)){
            memoList.remove(id);
            return id;
        }else{
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
