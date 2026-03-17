package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.JobApplication;
import com.examly.springapp.service.JobApplicationService;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService service;

    // CREATE
    @PostMapping
    public ResponseEntity<JobApplication> createJobApplication(@RequestBody JobApplication jobApplication) {
        if (jobApplication == null) {
            return ResponseEntity.badRequest().build();
        }
        JobApplication created = service.create(jobApplication);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobApplications() {
        List<JobApplication> list = service.read();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobApplicationById(@PathVariable Long id) {
        Optional<JobApplication> obj = service.getById(id);
        if (obj.isPresent()) {
            return ResponseEntity.ok(obj.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job application not found");
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateJobApplication(@PathVariable Long id, @RequestBody JobApplication jobApplication) {
        jobApplication.setApplicationId(id);
        JobApplication updated = service.update(jobApplication);
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
