package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.InterviewFeedback;

@Repository
public interface InterviewFeedbackRepository extends JpaRepository<InterviewFeedback, Long> 
{
    List<InterviewFeedback> findByJobApplicationApplicationId(Long applicationId);
}
