package com.greedy.pilala.classes.entity;

import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.member.entity.Member;
import com.greedy.pilala.teacher.dto.TeacherDto;
import com.greedy.pilala.teacher.entity.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="TBL_CLASS")
@SequenceGenerator(name="CLASS_SEQ_GENERATOR", sequenceName="SEQ_CLASS_CODE", initialValue = 1, allocationSize = 1)
public class Class {

    @Id
    @Column(name="CLASS_CODE")
    private Long classCode;

    @Column(name="CLASS_NAME")
    private String className;

    @Column(name="CLASS_DATE")
    private java.util.Date classDate;

    @Column(name="CLASS_ROOM")
    private String classRoom;

    @Column(name="START_TIME")
    private String startTime;

    @Column(name="END_TIME")
    private String endTime;

    @Column(name="NUM_STUDENT")
    private Integer numStudent;

    @ManyToOne
    @JoinColumn(name="MEMBER_CODE")
    private Member member;

    @ManyToOne
    @JoinColumn(name="TEACHER_CODE")
    private Teacher teacher;

    @Column(name="CLASS_IMAGE_URL")
    private String classImageUrl;


}
