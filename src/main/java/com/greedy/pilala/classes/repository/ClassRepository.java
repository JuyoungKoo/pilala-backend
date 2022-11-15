package com.greedy.pilala.classes.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.greedy.pilala.classes.entity.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {

}
