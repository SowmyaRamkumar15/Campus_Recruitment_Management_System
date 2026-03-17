package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody User user){
        try { return new ResponseEntity<>(service.create(user), HttpStatus.CREATED); }
        catch(Exception e){ return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll(){
        List<User> list = service.read();
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : ResponseEntity.ok(list);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        Optional<User> obj = service.getById(id);
        return obj.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(obj.get());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        Optional<User> obj = service.getById(id);
        if(obj.isPresent()){
            user.setUserId(id);
            return ResponseEntity.ok(service.update(user));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<User> obj = service.getById(id);
        if(obj.isPresent()){ service.delete(id); return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
