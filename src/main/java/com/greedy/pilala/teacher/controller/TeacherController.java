package com.greedy.pilala.teacher.controller;

import com.greedy.pilala.common.ResponseDto;
import com.greedy.pilala.common.paging.Pagenation;
import com.greedy.pilala.common.paging.PagingButtonInfo;
import com.greedy.pilala.common.paging.ResponseDtoWithPaging;
import com.greedy.pilala.member.dto.MemberDto;
import com.greedy.pilala.teacher.dto.TeacherDto;
import com.greedy.pilala.teacher.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
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


    @GetMapping("/teachers")
    public ResponseEntity<ResponseDto> selectTeacherList(@RequestParam(name="page", defaultValue = "1") int page){

        log.info("[TeacherController] selectTeacherList Start =============================");
        log.info("[TeacherController] page : {}" , page);

        Page<TeacherDto> teacherDtoList = teacherService.selectTeacherList(page);

        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(teacherDtoList);

        log.info("[TeacherController] pageInfo" , pageInfo);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(teacherDtoList.getContent());

        log.info("[TeacherController] selectMemberList End =============================");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"강사리스트 조회성공", responseDtoWithPaging));

    }




}
