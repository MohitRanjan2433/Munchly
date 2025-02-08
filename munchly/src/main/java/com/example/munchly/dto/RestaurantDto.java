package com.example.munchly.dto;


import lombok.Data;

@Data
public class RestaurantDto {

    private Long id;
    private String name;
    private String address;
    private Long phoneNumber;
    private String email;
    private Integer rating;

//    private UserDto manager;
}
