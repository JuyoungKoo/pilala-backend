package com.greedy.pilala.member.service;

import javax.transaction.Transactional;

import com.greedy.pilala.exception.DuplicatedUsernameException;
import com.greedy.pilala.jwt.TokenProvider;
import com.greedy.pilala.member.dto.TokenDto;
import com.greedy.pilala.member.exception.LoginFailedException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.member.entity.Member;
import com.greedy.pilala.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final TokenProvider tokenProvider;
	
	public AuthService(MemberRepository memberRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder ,TokenProvider tokenProvider) {
		
		this.memberRepository = memberRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		
	}
	
	@Transactional
	public MemberDto join(MemberDto memberDto) {
		
		log.info("[AuthService] join Start =============================");
		log.info("[AuthService] memberDto : {} " , memberDto);
		
		if(memberRepository.findByMemberEmail(memberDto.getMemberEmail()) != null) {
			log.info("[AuthService] 이메일이 중복 됩니다.");
			throw new DuplicatedUsernameException("이메일이 중복됩니다.");
		}
		
		memberDto.setMemberPassword(passwordEncoder.encode(memberDto.getMemberPassword()));
		memberRepository.save(modelMapper.map(memberDto, Member.class));
		
		log.info("[AuthService] join End =============================");
		
		return memberDto;
	}

	/* 2. 로그인 */
	public TokenDto login(MemberDto memberDto) {

		log.info("[AuthService] login start =================");
		log.info("[AuthService] MemberDto : {}", memberDto );

		// 1. 아이디 조회
		Member member = memberRepository.findByMemberId(memberDto.getMemberId())
				.orElseThrow(()-> new LoginFailedException("잘못된 아이디 또는 비밀번호 입니다."));

		// 2. 비밀번호 매칭
		if(!passwordEncoder.matches(memberDto.getMemberPassword(), member.getMemberPassword())) {
			log.info("[AuthService] Password Match Fail!!!");
			throw new LoginFailedException("잘못 된 아이디 또는 비밀번호 입니다.");

		}

		// 3. 토큰 발급
		TokenDto tokenDto = tokenProvider.generateTokenDto(modelMapper.map(member, MemberDto.class));
		log.info("[AuthService] tokenDto : {}", tokenDto);

		log.info("[AuthService] login End =================");

		return tokenDto;

	}
}
