package com.greedy.pilala.member.controller;

import com.greedy.pilala.common.ResponseDto;
import com.greedy.pilala.common.paging.Pagenation;
import com.greedy.pilala.common.paging.PagingButtonInfo;
import com.greedy.pilala.common.paging.ResponseDtoWithPaging;
import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.member.service.MemberService;
import com.greedy.pilala.reservation.dto.ReservationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
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


    @GetMapping("/members")
    public ResponseEntity<ResponseDto> selectMemberList(@RequestParam(name="page", defaultValue = "1") int page){

        log.info("[MemberController] selectMemberList Start =============================");
        log.info("[MemberController] page : {}" , page);

        Page<MemberDto> memberDtoList = memberService.selectMemberList(page);

        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(memberDtoList);

        log.info("[MemberController] pageInfo" , pageInfo);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(memberDtoList.getContent());

        log.info("[MemberController] selectMemberList End =============================");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"멤버리스트 조회성공", responseDtoWithPaging));

    }

    @PutMapping("/members")
    public ResponseEntity<ResponseDto> updateMemberRole(@RequestBody MemberDto memberDto){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "권한 업데이트 완료", memberService.updateMemberRole(memberDto)));

    }
	
	

}
