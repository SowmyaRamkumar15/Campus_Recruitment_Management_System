
package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Department;
import com.examly.springapp.service.DepartmentService;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // CREATE
    @PostMapping("/departments")
    public ResponseEntity<Department> create(@RequestBody Department dept) {
        try {
            Department obj = departmentService.create(dept);
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAll() {
        List<Department> list = departmentService.read();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getById(@PathVariable Long id)
    {
        Optional<Department> obj = departmentService.getById(id);
        if (obj.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(obj.get(), HttpStatus.OK);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id, @RequestBody Department dept)
    {
        Optional<Department> obj = departmentService.getById(id);
        if (obj.isPresent()) {
            dept.setDepartmentId(id);
            Department updated = departmentService.update(dept);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        Optional<Department> obj = departmentService.getById(id);
        if (obj.isPresent())
        {
            departmentService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/departments/page/{page}/{size}")
    public ResponseEntity<Page<Department>> getDepartments(@PathVariable int page , @PathVariable int size)
    {
        Page<Department> res = departmentService.getDepartments(page, size);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}