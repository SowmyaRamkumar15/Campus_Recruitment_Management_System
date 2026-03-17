package com.examly.springapp.dto;

import java.util.List;
import com.examly.springapp.model.JobOpening;
import com.examly.springapp.model.JobApplication;
import com.examly.springapp.model.InterviewFeedback;

public class StudentDashboard {

    private List<JobOpening> eligibleJobs;

    private List<JobApplication> appliedJobs;

    private List<InterviewFeedback> feedbacks;

    public List<JobOpening> getEligibleJobs() {
        return eligibleJobs;
    }

    public void setEligibleJobs(List<JobOpening> eligibleJobs) {
        this.eligibleJobs = eligibleJobs;
    }

    public List<JobApplication> getAppliedJobs() {
        return appliedJobs;
    }

    public void setAppliedJobs(List<JobApplication> appliedJobs) {
        this.appliedJobs = appliedJobs;
    }

    public List<InterviewFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<InterviewFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}