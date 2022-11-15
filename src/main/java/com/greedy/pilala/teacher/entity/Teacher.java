package com.greedy.pilala.teacher.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="TBL_TEACHER")
@SequenceGenerator(name="TEACHER_SEQ_GENERATOR", sequenceName = "SEQ_TEACHER_CODE", initialValue = 1, allocationSize = 1)
@DynamicInsert
public class Teacher {

    @Id
    @Column(name= "TEACHER_CODE")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TEACHER_SEQ_GENERATOR")
    private Long teacherCode;

    @Column(name= "TEACHER_NAME")
    private String teacherName;
}
