package com.greedy.pilala.classes.dto;

import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.teacher.dto.TeacherDto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClassDto {

    private Long classCode;
    private String className;
    private String classDate;
    private String classRoom;
    private String startTime;
    private String endTime;
    private Integer numStudent;
    private TeacherDto teacher;
    private String classImageUrl;

    private MultipartFile classImage;
}
