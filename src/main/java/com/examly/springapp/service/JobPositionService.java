package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.JobPosition;

public interface JobPositionService {

    JobPosition create(JobPosition jobPosition);

    List<JobPosition> read();

    Optional<JobPosition> getById(Long id);

    JobPosition update(JobPosition jobPosition);

    void delete(Long id);

    List<JobPosition> searchByTitle(String keyword);
}