package com.example.munchly.dto;

import lombok.Data;

@Data
public class MenuItemDto {

    private Long id;
    private String name;
    private String description;
    private Long price;

    private RestaurantDto restaurant;
}
