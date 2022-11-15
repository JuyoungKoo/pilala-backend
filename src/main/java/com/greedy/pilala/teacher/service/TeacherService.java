package com.greedy.pilala.teacher.service;

import com.greedy.pilala.teacher.dto.TeacherDto;
import com.greedy.pilala.teacher.entity.Teacher;
import com.greedy.pilala.teacher.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public TeacherService(TeacherRepository teacherRepository,ModelMapper modelMapper){
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public TeacherDto registerTeacher(TeacherDto teacherDto) {

        log.info("[TeacherService] registerTeacher Start =========================");
        log.info("[TeacherService] teacherDto : {}", teacherDto);

        teacherRepository.save(modelMapper.map(teacherDto, Teacher.class));

        log.info("[TeacherService] registerTeacher End =========================");

        return teacherDto;
    }


    public Object deleteTeacher(Long teacherCode) {

        log.info("[TeacherService] deleteTeacher Start =========================");
        log.info("[TeacherService] teacherCode : {}", teacherCode);

        Teacher selectedTeacher = teacherRepository.findById(teacherCode)
                .orElseThrow(()-> new RuntimeException("해당 강사가 존재하지 않습니다."));

       teacherRepository.delete(selectedTeacher);

        log.info("[TeacherService] deleteTeacher End =========================");

        return teacherCode;


    }
}
