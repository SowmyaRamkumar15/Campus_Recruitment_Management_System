package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.examly.springapp.model.JobOpening;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {
}