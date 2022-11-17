package com.greedy.pilala.reservation.service;

import com.greedy.pilala.reservation.dto.ReservationDto;
import com.greedy.pilala.reservation.entity.Reservation;
import com.greedy.pilala.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ReservationService {

    ModelMapper modelMapper;
    ReservationRepository reservationRepository;

    public ReservationService(ModelMapper modelMapper, ReservationRepository reservationRepository){
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;

    }
    @Transactional
    public ReservationDto insertReservation(ReservationDto reservationDto) {

        log.info("[ReservationService] insertReservation Start =============");
        log.info("[ReservationService] reservationDto : {} ", reservationDto);

        reservationRepository.save(modelMapper.map(reservationDto, Reservation.class));

        log.info("[ReservationService] insertReservation End =============");

        return reservationDto;
    }

    @Transactional
    public Object cancelReservation(ReservationDto reservationDto) {

        log.info("[ReservationService] cancelReservation Start =============");
        log.info("[ReservationService] reservationDto : {} ", reservationDto);

        Reservation reservation = reservationRepository.findById(reservationDto.getReservationCode())
                        .orElseThrow(()->new RuntimeException("해당 수업이 존재하지 않습니다."));

        reservation.update(reservationDto.getReservationStatus());

        reservationRepository.save(reservation);

        log.info("[ReservationService] reservation : {} ", reservation);
        log.info("[ReservationService] cancelReservation End =============");

        return reservationDto;

    }
}
