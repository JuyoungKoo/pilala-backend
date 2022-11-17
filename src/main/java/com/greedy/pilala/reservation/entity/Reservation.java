package com.greedy.pilala.reservation.entity;


import com.greedy.pilala.member.entity.Member;
import com.greedy.pilala.classes.entity.Class;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TBL_RESERVATION")
@DynamicInsert
public class Reservation {

    @Id
    @Column(name = "RESERVATION_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationCode;

    @Column(name = "RESERVATION_STATUS")
    private String reservationStatus;

    @ManyToOne
    @JoinColumn(name = "MEMBER_CODE")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CLASS_CODE")
    private Class classes;

    @Column(name = "RESERVATION_DATE")
    private java.util.Date reservationDate;


    public void update(String reservationStatus){
        this.reservationStatus = reservationStatus;
    }
}
