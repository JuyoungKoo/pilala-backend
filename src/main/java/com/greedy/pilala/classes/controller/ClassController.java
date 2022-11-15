package com.greedy.pilala.classes.controller;

import com.greedy.pilala.classes.dto.ClassDto;
import com.greedy.pilala.classes.service.ClassService;
import com.greedy.pilala.common.ResponseDto;
import com.greedy.pilala.common.paging.Pagenation;
import com.greedy.pilala.common.paging.PagingButtonInfo;
import com.greedy.pilala.common.paging.ResponseDtoWithPaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService){

        this.classService = classService;

    }


    @GetMapping("/classes")
    public ResponseEntity<ResponseDto> selectClassList(@RequestParam(name="page", defaultValue = "1") int page){

        log.info("[ClassController] selectClassList Start ================================");
        log.info("[ClassController] page : {}", page);

        Page<ClassDto> ClassDtoList = classService.selectClassList(page);

        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(ClassDtoList);

        log.info("[ProductController] pageInfo : {}", pageInfo);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(ClassDtoList.getContent());

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

    }

    @PostMapping("/classes")
    public ResponseEntity<ResponseDto> insertClass(@ModelAttribute ClassDto classDto){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "강의 등록 성공", classService.insertClass(classDto)));

    }
}
