package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Department;
import com.examly.springapp.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department create(Department dept) {
        return departmentRepository.save(dept);
    }

    @Override
    public List<Department> read() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department update(Department dept) {
        return departmentRepository.save(dept);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Page<Department> getDepartments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return departmentRepository.findAll(pageable);
    }
}