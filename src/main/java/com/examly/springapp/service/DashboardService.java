package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.StudentDashboard;
import com.examly.springapp.repository.JobOpeningRepository;
import com.examly.springapp.repository.JobApplicationRepository;
import com.examly.springapp.repository.InterviewFeedbackRepository;

@Service
public class DashboardService {

    @Autowired
    private JobOpeningRepository jobOpeningRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private InterviewFeedbackRepository feedbackRepository;

    public StudentDashboard getDashboard(Long studentId, Long departmentId){

        StudentDashboard dashboard = new StudentDashboard();

        dashboard.setEligibleJobs(
                jobOpeningRepository.findAll()
        );

        dashboard.setAppliedJobs(
                jobApplicationRepository.findAll()
        );

        dashboard.setFeedbacks(
                feedbackRepository.findAll()
        );

        return dashboard;
    }
}