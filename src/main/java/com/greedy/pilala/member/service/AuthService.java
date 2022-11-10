package com.greedy.pilala.member.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.member.entity.Member;
import com.greedy.pilala.member.exception.DuplicatedUsernameException;
import com.greedy.pilala.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	
	public AuthService(MemberRepository memberRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		
		this.memberRepository = memberRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		
	}
	
	@Transactional
	public Object join(MemberDto memberDto) {
		
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

}
