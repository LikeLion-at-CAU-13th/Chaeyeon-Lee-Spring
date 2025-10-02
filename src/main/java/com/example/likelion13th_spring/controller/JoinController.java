package com.example.likelion13th_spring.controller;

import com.example.likelion13th_spring.Service.MemberService;
import com.example.likelion13th_spring.dto.request.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequestDto joinRequestDto) {
        try{
            memberService.join(joinRequestDto);
        } catch(IllegalArgumentException e){
            //예외상황 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }
}
