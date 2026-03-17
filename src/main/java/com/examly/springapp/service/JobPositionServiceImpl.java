package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.JobPosition;
import com.examly.springapp.repository.JobPositionRepository;

@Service
public class JobPositionServiceImpl implements JobPositionService {

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Override
    public JobPosition create(JobPosition jobPosition) {
        return jobPositionRepository.save(jobPosition);
    }

    @Override
    public List<JobPosition> read() {
        return jobPositionRepository.findAll();
    }

    @Override
    public Optional<JobPosition> getById(Long id) {
        return jobPositionRepository.findById(id);
    }

    @Override
    public JobPosition update(JobPosition jobPosition) {
        return jobPositionRepository.save(jobPosition);
    }

    @Override
    public void delete(Long id) {
        jobPositionRepository.deleteById(id);
    }

    @Override
    public List<JobPosition> searchByTitle(String keyword) {
        return jobPositionRepository.findByPositionTitleContainingIgnoreCase(keyword);
    }
}