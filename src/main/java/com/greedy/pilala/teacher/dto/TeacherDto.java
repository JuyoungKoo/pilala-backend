package com.greedy.pilala.teacher.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TeacherDto {

    private Long teacherCode;
    private String teacherName;
}
