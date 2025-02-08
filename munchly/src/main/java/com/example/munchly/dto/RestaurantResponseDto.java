package com.example.munchly.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RestaurantResponseDto {

    private Long id;
    private String name;
    private String address;
    private Long phoneNumber;
    private String email;
    private Integer rating;
}
