package com.example.munchly.dto;

import lombok.Data;

@Data
public class MenuItemResponseDto {

    private Long id;
    private String name;
    private String description;
    private Long price;
}
