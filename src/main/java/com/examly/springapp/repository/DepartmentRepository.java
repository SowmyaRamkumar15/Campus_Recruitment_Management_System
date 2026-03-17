package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{
    
}
