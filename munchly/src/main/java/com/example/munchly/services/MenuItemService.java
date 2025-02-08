package com.example.munchly.services;

import com.example.munchly.dto.MenuItemDto;
import com.example.munchly.dto.MenuItemResponseDto;
import com.example.munchly.entities.MenuItem;
import com.example.munchly.entities.Restaurant;
import com.example.munchly.entities.User;
import com.example.munchly.exception.ResourceNotFoundException;
import com.example.munchly.repositories.MenuItemRepository;
import com.example.munchly.repositories.RestaurantRepository;
import com.example.munchly.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuItemService {

    private final ModelMapper modelMapper;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public MenuItemDto createMenuItem(MenuItemDto inputItems){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userDetails = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this id "+ user.getId()));

        Restaurant restroDetails = restaurantRepository.findByManagerId(user.getId());

        if(restroDetails == null){
            throw new ResourceNotFoundException("No restaurant found for the logged-in manager.");
        }

        MenuItem menuItem = modelMapper.map(inputItems, MenuItem.class);
        menuItem.setRestaurant(restroDetails);

        MenuItem savedMenu = menuItemRepository.save(menuItem);
        return modelMapper.map(savedMenu, MenuItemDto.class);
    }

    public List<MenuItemDto> getAllMenuItems(){
        return menuItemRepository
                .findAll()
                .stream()
                .map(MenuItem -> modelMapper.map(MenuItem, MenuItemDto.class))
                .collect(Collectors.toList());
    }

    public List<MenuItemResponseDto> getByRestaurantId(Long restaurantId){
        Restaurant exists = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant with this id not found " + restaurantId));

        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);

        return menuItems
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemResponseDto.class))
                .collect(Collectors.toList());
    }
}
