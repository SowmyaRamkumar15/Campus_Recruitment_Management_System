package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    private UserDTO toDTO(User user){
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody User user){
        User created = service.create(user);
        return new ResponseEntity<>(toDTO(created), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAll(){
        List<UserDTO> list = service.read().stream().map(this::toDTO).collect(Collectors.toList());
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : ResponseEntity.ok(list);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id){
        Optional<User> obj = service.getById(id);
        return obj.map(user -> ResponseEntity.ok(toDTO(user)))
                  .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody User user){
        Optional<User> existing = service.getById(id);
        if(existing.isPresent()){
            user.setUserId(id);
            return ResponseEntity.ok(toDTO(service.update(user)));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<User> obj = service.getById(id);
        if(obj.isPresent()){
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}