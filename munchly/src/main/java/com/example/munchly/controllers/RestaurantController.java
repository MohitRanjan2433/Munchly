package com.example.munchly.controllers;


import com.example.munchly.dto.RestaurantDto;
import com.example.munchly.dto.RestaurantResponseDto;
import com.example.munchly.services.RestaurantService;
import jdk.jfr.SettingDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;


    @PostMapping("/create")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto){
        RestaurantDto createRest = restaurantService.createRestaurant(restaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRest);
    }

    @GetMapping
    public List<RestaurantResponseDto> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restroId}")
    public RestaurantResponseDto getRestaurantById(@PathVariable Long restroId){
        return restaurantService.getRestroById(restroId);
    }
}
