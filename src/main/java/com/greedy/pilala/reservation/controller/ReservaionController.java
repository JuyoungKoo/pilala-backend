package com.greedy.pilala.reservation.controller;

import com.greedy.pilala.common.ResponseDto;
import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.reservation.dto.ReservationDto;
import com.greedy.pilala.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ReservaionController {

    ReservationService reservationService;

    public ReservaionController(ReservationService reservationService){

        this.reservationService = reservationService;

    }


    @PostMapping("/reservations")
    public ResponseEntity<ResponseDto> insertReservation(@RequestBody ReservationDto reservationDto,
                                                         @AuthenticationPrincipal MemberDto member){
        reservationDto.setMember(member);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "강의신청완료", reservationService.insertReservation(reservationDto)));

    }


    @PutMapping("/reservations")
    public ResponseEntity<ResponseDto> cancelReservation(@RequestBody ReservationDto reservationDto,
                                                         @AuthenticationPrincipal MemberDto member){
        reservationDto.setMember(member);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "강의취소완료", reservationService.cancelReservation(reservationDto)));

    }


}
