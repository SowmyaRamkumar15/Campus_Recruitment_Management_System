package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.examly.springapp.model.Department;

public interface DepartmentService {

    Department create(Department dept);

    List<Department> read();

    Optional<Department> getById(Long id);

    Department update(Department dept);

    void delete(Long id);

    Page<Department> getDepartments(int page, int size);
}