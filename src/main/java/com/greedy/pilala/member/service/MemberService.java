package com.greedy.pilala.member.service;

import com.greedy.pilala.exception.UserNotFoundException;
import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.member.entity.Member;
import com.greedy.pilala.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper){

        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;

    }
    public MemberDto selectMyInfo(String memberId) {

        log.info("[MemberService] selectMyInfo start =================");
        log.info("[MemberService] memberId : {} ", memberId);

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(()-> new UserNotFoundException(memberId + "를 찾을 수 없습니다."));

        log.info("[MemberService] memberId : {} ", member);

        log.info("[MemberService] selectMyInfo End =================");

        return modelMapper.map(member, MemberDto.class);
    }
}
