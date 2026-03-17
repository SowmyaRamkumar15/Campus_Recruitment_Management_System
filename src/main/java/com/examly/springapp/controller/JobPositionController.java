package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.JobPosition;
import com.examly.springapp.service.JobPositionService;

@RestController
@RequestMapping("/api")
public class JobPositionController {

    @Autowired
    private JobPositionService jobPositionService;

    @PostMapping("/job-positions")
    public ResponseEntity<JobPosition> create(@RequestBody JobPosition job) {
        try {
            JobPosition obj = jobPositionService.create(job);
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/job-positions")
    public ResponseEntity<List<JobPosition>> getAll() {
        List<JobPosition> list = jobPositionService.read();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/job-positions/{id}")
    public ResponseEntity<JobPosition> getById(@PathVariable Long id) {
        Optional<JobPosition> obj = jobPositionService.getById(id);
        if (obj.isPresent()) {
            return new ResponseEntity<>(obj.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/job-positions/{id}")
    public ResponseEntity<JobPosition> update(@PathVariable Long id, @RequestBody JobPosition job) {
        Optional<JobPosition> obj = jobPositionService.getById(id);
        if (obj.isPresent()) 
        {
            job.setPositionId(id);
            JobPosition updated = jobPositionService.update(job);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

    @DeleteMapping("/job-positions/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<JobPosition> obj = jobPositionService.getById(id);
        if (obj.isPresent())
        {
            jobPositionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/job-positions/search/{keyword}")
    public ResponseEntity<List<JobPosition>> searchJobPositions(@PathVariable String keyword) {
        List<JobPosition> list = jobPositionService.searchByTitle(keyword);
        if (list.isEmpty()) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }