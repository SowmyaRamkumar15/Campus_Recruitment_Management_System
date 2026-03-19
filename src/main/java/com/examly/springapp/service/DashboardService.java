package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.StudentDashboard;
import com.examly.springapp.model.JobApplication;
import com.examly.springapp.model.JobOpening;
import com.examly.springapp.model.InterviewFeedback;
import com.examly.springapp.repository.JobApplicationRepository;
import com.examly.springapp.repository.JobOpeningRepository;
import com.examly.springapp.repository.InterviewFeedbackRepository;

import java.util.List;
import java.util.stream.Collectors;

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

        // Eligible jobs for student's department
        List<JobOpening> eligibleJobs = jobOpeningRepository.findAll().stream()
                .filter(job -> job.getEligibleDepartments().stream()
                        .anyMatch(dept -> dept.getDepartmentId().equals(departmentId)))
                .collect(Collectors.toList());

        // Jobs applied by student
        List<JobApplication> appliedJobs = jobApplicationRepository.findAll().stream()
                .filter(app -> app.getCandidate().getUserId().equals(studentId))
                .collect(Collectors.toList());

        // Feedbacks for student's applications
        List<InterviewFeedback> feedbacks = feedbackRepository.findAll().stream()
                .filter(f -> f.getJobApplication().getCandidate().getUserId().equals(studentId))
                .collect(Collectors.toList());

        dashboard.setEligibleJobs(eligibleJobs);
        dashboard.setAppliedJobs(appliedJobs);
        dashboard.setFeedbacks(feedbacks);

        return dashboard;
    }
}