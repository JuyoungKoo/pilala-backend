package com.greedy.pilala.reservation.dto;

import com.greedy.pilala.classes.dto.ClassDto;
import com.greedy.pilala.member.dto.MemberDto;
import lombok.Data;

@Data
public class ReservationDto {

    private Long reservationCode;
    private String reservationStatus;
    private MemberDto member;
    private ClassDto classes;
    private java.util.Date reservationDate;


}
