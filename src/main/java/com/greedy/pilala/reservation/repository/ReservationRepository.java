package com.greedy.pilala.reservation.repository;

import com.greedy.pilala.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
