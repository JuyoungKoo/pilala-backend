package com.greedy.pilala.classes.service;


import com.greedy.pilala.classes.dto.ClassDto;
import com.greedy.pilala.classes.entity.Class;
import com.greedy.pilala.classes.repository.ClassRepository;
import com.greedy.pilala.teacher.entity.Teacher;
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


    public ClassDto selectClassDetail(Long classCode) {
        log.info("[ClassService] selectClassDetail Start =====================" );
        log.info("[ClassService] classCode = {}" , classCode );

        Class classes = classRepository.findById(classCode)
                .orElseThrow(()-> new IllegalArgumentException("해당상품이 없습니다. classCode" + classCode));
        ClassDto classDto = modelMapper.map(classes, ClassDto.class);
        classDto.setClassImageUrl(IMAGE_URL + classDto.getClassImageUrl());

        log.info("[ClassService] classDto = {}" , classDto);

        log.info("[ClassService] selectClassDetail End =====================" );

        return classDto;




    }


    @Transactional
    public ClassDto insertClass(ClassDto classDto) {

        log.info("[ClassService] insertClass Start ===================================");
        log.info("[ClassService] classDto : {}", classDto);
        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        try {
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, classDto.getClassImage());
            classDto.setClassImageUrl(replaceFileName);

            log.info("[ClassService] replaceFileName : {}", replaceFileName);

            classRepository.save(modelMapper.map(classDto, Class.class));

        } catch (IOException e) {
            e.printStackTrace();
            try {
                FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        log.info("[ClassService] insertClass End ===================================");

        return classDto;
    }

    @Transactional
    public ClassDto updateClass(ClassDto classDto) {

        log.info("[ClassService] updateClass Start ===================================");
        log.info("[ClassService] classDto : {}", classDto);

        String replaceFileName = null;

        try{
            Class oriClass = classRepository.findById(classDto.getClassCode())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. classCode= " + classDto.getClassCode()));
            String oriImage = oriClass.getClassImageUrl();

            if (classDto.getClassImage() != null) {

                /* 새로 입력 된 이미지 저장 */
                String imageName = UUID.randomUUID().toString().replace("-", "");
                replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, classDto.getClassImage());
                classDto.setClassImageUrl(replaceFileName);

                /* 기존에 저장 된 이미지 삭제*/
                FileUploadUtils.deleteFile(IMAGE_DIR, oriImage);

            } else {
                /* 이미지를 변경하지 않는 경우 */
                classDto.setClassImageUrl(oriImage);
            }

            /* 조회 했던 기존 엔티티의 내용을 수정 */
            oriClass.update(classDto.getClassName(),
                    classDto.getClassDate(),
                    classDto.getClassRoom(),
                    classDto.getStartTime(),
                    classDto.getEndTime(),
                    classDto.getNumStudent(),
                    modelMapper.map(classDto.getTeacher(), Teacher.class),
                    classDto.getClassImageUrl());

            classRepository.save(oriClass);

        } catch (IOException e) {
            e.printStackTrace();
            try {
                FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        log.info("[ProductService] updateProduct End ===================================");

        return classDto;
    }


}
