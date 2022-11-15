package com.greedy.pilala.teacher.repository;


import com.greedy.pilala.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
