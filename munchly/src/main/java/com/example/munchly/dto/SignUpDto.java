package com.example.munchly.dto;


import com.example.munchly.entities.enums.Role;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SignUpDto {

    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    private Long phoneNumber;
    private String address;
}
