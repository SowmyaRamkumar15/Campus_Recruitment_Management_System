package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.JobApplication;
import com.examly.springapp.model.JobPosition;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.JobApplicationRepository;
import com.examly.springapp.repository.JobPositionRepository;
import com.examly.springapp.repository.UserRepository;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Override
    public JobApplication create(JobApplication jobApplication) {

        Optional<User> candidateOpt = userRepository.findById(jobApplication.getCandidate().getUserId());
        candidateOpt.ifPresent(jobApplication::setCandidate);

        Optional<JobPosition> jobPositionOpt = jobPositionRepository.findById(jobApplication.getJobPosition().getPositionId());
        jobPositionOpt.ifPresent(jobApplication::setJobPosition);

        return repository.save(jobApplication);
    }

    @Override
    public List<JobApplication> read() {
        return repository.findAll();
    }

    @Override
    public Optional<JobApplication> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public JobApplication update(JobApplication jobApplication) {
        return repository.save(jobApplication);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}