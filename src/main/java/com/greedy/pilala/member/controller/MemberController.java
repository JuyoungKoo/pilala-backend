package com.greedy.pilala.member.controller;

import com.greedy.pilala.common.ResponseDto;
import com.greedy.pilala.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){

        this.memberService = memberService;
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseDto> selectMyInfo(@PathVariable String memberId){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"조회성공", memberService.selectMyInfo(memberId)));

    }
	
	
	

}
