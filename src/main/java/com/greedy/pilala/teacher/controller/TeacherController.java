package com.greedy.pilala.teacher.controller;

import com.greedy.pilala.common.ResponseDto;
import com.greedy.pilala.teacher.dto.TeacherDto;
import com.greedy.pilala.teacher.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService){

        this.teacherService = teacherService;

    }
    @PostMapping("/teachers")
    public ResponseEntity<ResponseDto> registerTeacher(@RequestBody TeacherDto teacherDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "강사등록성공", teacherService.registerTeacher(teacherDto)));
    }

    @DeleteMapping ("teachers/{teacherCode}")
    public ResponseEntity<ResponseDto> deleteTeacher(@PathVariable Long teacherCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "강사삭제성공", teacherService.deleteTeacher(teacherCode)));
    }




}
