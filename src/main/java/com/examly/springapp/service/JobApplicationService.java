package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.JobApplication;

public interface JobApplicationService {

    JobApplication create(JobApplication jobApplication);

    List<JobApplication> read();

    Optional<JobApplication> getById(Long id);

    JobApplication update(JobApplication jobApplication);

    void delete(Long id);
}