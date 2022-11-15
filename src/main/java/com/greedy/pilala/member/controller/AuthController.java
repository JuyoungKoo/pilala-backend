package com.greedy.pilala.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.pilala.common.ResponseDto;
import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.member.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		
		this.authService = authService;
		
	}
	
	/* 1. 회원가입 */
	@PostMapping("/join")
	public ResponseEntity<ResponseDto> join(@RequestBody MemberDto memberDto){
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "회원가입 성공", authService.join(memberDto)));
		
	}
	
	/* 2. 로그인 */
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody MemberDto memberDto){

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"로그인성공", authService.login(memberDto)));
	}

}
