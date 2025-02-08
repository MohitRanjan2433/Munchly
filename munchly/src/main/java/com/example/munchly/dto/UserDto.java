package com.example.munchly.dto;


import com.example.munchly.entities.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    private Long phoneNumber;
    private boolean verified;
    private String otp;
    private boolean active;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
