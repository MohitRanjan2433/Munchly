package com.example.munchly.services;


import com.example.munchly.dto.RestaurantDto;
import com.example.munchly.dto.RestaurantResponseDto;
import com.example.munchly.entities.Restaurant;
import com.example.munchly.entities.User;
import com.example.munchly.exception.ResourceNotFoundException;
import com.example.munchly.repositories.RestaurantRepository;
import com.example.munchly.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantDto createRestaurant(RestaurantDto inputRestaurant){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userDetails = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + user.getId()));

        if(!userDetails.isVerified()){
            throw new IllegalArgumentException("User is not verified.");
        }

        Restaurant existingRestaurant = restaurantRepository.findByEmail(inputRestaurant.getEmail());

        if(existingRestaurant != null){
            throw new IllegalArgumentException("Restaurant with this email aready exists");
        }

        Restaurant restaurant = modelMapper.map(inputRestaurant, Restaurant.class);
        restaurant.setManager(user);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    public List<RestaurantResponseDto> getAllRestaurants(){
        return restaurantRepository.findAll()
                .stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantResponseDto.class))
                .collect(Collectors.toList());
    }

    public RestaurantResponseDto getRestroById(Long restroId){
        Restaurant restaurant = restaurantRepository.findById(restroId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant with this id dont exists "+ restroId));

        return modelMapper.map(restaurant, RestaurantResponseDto.class);
    };

}
