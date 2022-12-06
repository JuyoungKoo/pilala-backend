package com.greedy.pilala.member.service;

import com.greedy.pilala.exception.UserNotFoundException;
import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.member.entity.Member;
import com.greedy.pilala.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public Page<MemberDto> selectMemberList(int page) {

        log.info("[MemberService] selectMemberList start =================");
        log.info("[MemberService] page : {} ", page);

        Pageable pageable = PageRequest.of(page -1, 10, Sort.by("memberCode").descending());

        Page<Member> memberList = memberRepository.findAll(pageable);
        Page<MemberDto> memberDtoList = memberList.map(member -> modelMapper.map(member, MemberDto.class));

        log.info("[MemberService] memberDtoList : {} ", memberDtoList.getContent());
        log.info("[MemberService] selectMemberList End =================");

        return memberDtoList;

    }

    public MemberDto updateMemberRole(MemberDto memberDto) {

        log.info("[MemberService] updateMemberRole start =================");
        log.info("[MemberService] memberDto : {} ", memberDto);

        Member member = memberRepository.findById(memberDto.getMemberCode())
                .orElseThrow(()->new RuntimeException("멤버가 존재하지 않습니다."));

        member.update(memberDto.getMemberRole());

        memberRepository.save(member);

        log.info("[MemberService] member : {} ", member);
        log.info("[MemberService] updateMemberRole End =================");

        return memberDto;


    }

    public Object selectMemberDetail(Long memberCode) {

        log.info("[MemberService] selectMemberDetail start =================");
        log.info("[MemberService] memberCode : {} ", memberCode);

        Member member = memberRepository.findById(memberCode)
                .orElseThrow(()-> new UserNotFoundException(memberCode + "를 찾을 수 없습니다."));

        log.info("[MemberService] memberId : {} ", member);

        log.info("[MemberService] selectMemberDetail End =================");

        return modelMapper.map(member, MemberDto.class);
    }
}
