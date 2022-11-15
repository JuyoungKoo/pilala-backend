package com.greedy.pilala.classes.service;


import com.greedy.pilala.classes.dto.ClassDto;
import com.greedy.pilala.classes.entity.Class;
import com.greedy.pilala.classes.repository.ClassRepository;
import com.greedy.pilala.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

    public ClassService(ClassRepository classRepository,ModelMapper modelMapper) {
        this.classRepository = classRepository;
        this.modelMapper = modelMapper;
    }
    public Page<ClassDto> selectClassList(int page) {

        log.info("[ClassService] selectClassList Start =====================" );

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("classCode").descending());

        Page<Class> classList = classRepository.findAll(pageable);
        Page<ClassDto> classDtoList = classList.map(classes -> modelMapper.map(classes, ClassDto.class));
        /* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
        classDtoList.forEach(classes -> classes.setClassImageUrl(IMAGE_URL + classes.getClassImageUrl() ));

        log.info("[ClassService] classDtoList : {}", classDtoList.getContent());

        log.info("[ClassService] selectClassList End =====================" );

        return classDtoList;
    }


    @Transactional
    public Object insertClass(ClassDto classDto) {

        log.info("[ClassService] insertClass Start =====================" );
        log.info("[ClassService] classDto : {}", classDto);
        String imageName = UUID.randomUUID().toString().replace("-","");
        String replaceFileName = null;

        try{
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, classDto.getClassImage());
            classDto.setClassImageUrl(replaceFileName);
        } catch (IOException e){
            e.printStackTrace();
        }

        log.info("[ClassService] insertClass End =====================" );

        return classDto;

    }

}
