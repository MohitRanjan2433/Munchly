//package com.example.munchly.utils;
//
//
//import com.example.munchly.dto.RestaurantDto;
//import com.example.munchly.entities.User;
//import com.example.munchly.services.RestaurantService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class PostSecurity {
//
//    private final RestaurantService restaurantService;
//
//    public boolean isOwnerOfRestro(Long restrauntId){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        RestaurantDto restaurantDto = restaurantService.getRestroById(restrauntId);
//        return restaurantDto.getManager().getId().equals(user.getId());
//    }
//}
