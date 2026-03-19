package com.examly.springapp.dto;

import com.examly.springapp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private Role role;
}