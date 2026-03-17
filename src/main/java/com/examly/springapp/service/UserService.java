package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.User;

public interface UserService {

    User create(User user);

    List<User> read();

    Optional<User> getById(Long id);

    User update(User user);

    void delete(Long id);
}