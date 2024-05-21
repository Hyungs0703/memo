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
}
