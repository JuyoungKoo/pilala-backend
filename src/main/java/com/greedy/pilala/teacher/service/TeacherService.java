package com.greedy.pilala.teacher.service;

import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.member.entity.Member;
import com.greedy.pilala.teacher.dto.TeacherDto;
import com.greedy.pilala.teacher.entity.Teacher;
import com.greedy.pilala.teacher.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @Transactional
    public Object deleteTeacher(Long teacherCode) {

        log.info("[TeacherService] deleteTeacher Start =========================");
        log.info("[TeacherService] teacherCode : {}", teacherCode);

        Teacher selectedTeacher = teacherRepository.findById(teacherCode)
                .orElseThrow(()-> new RuntimeException("해당 강사가 존재하지 않습니다."));

       teacherRepository.delete(selectedTeacher);

        log.info("[TeacherService] deleteTeacher End =========================");

        return teacherCode;


    }

    public Page<TeacherDto> selectTeacherList(int page) {

        log.info("[TeacherService] selectTeacherList start =================");
        log.info("[TeacherService] page : {} ", page);

        Pageable pageable = PageRequest.of(page -1, 10, Sort.by("teacherCode").descending());

        Page<Teacher> teacherList = teacherRepository.findAll(pageable);
        Page<TeacherDto> teacherDtoList = teacherList.map(teacher -> modelMapper.map(teacher, TeacherDto.class));

        log.info("[TeacherService] teacherDtoList : {} ", teacherDtoList.getContent());
        log.info("[TeacherService] selectTeacherList End =================");

        return teacherDtoList;

    }
}
