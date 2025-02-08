package com.example.munchly.controllers;

import com.example.munchly.dto.MenuItemDto;
import com.example.munchly.dto.MenuItemResponseDto;
import com.example.munchly.services.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/create")
    public MenuItemDto createMenuItem(@RequestBody MenuItemDto menuItemDto){
        return menuItemService.createMenuItem(menuItemDto);
    }

    @GetMapping
    public List<MenuItemDto> getAllMenuItems(){
        return menuItemService.getAllMenuItems();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<MenuItemResponseDto>> getMenuItemsByRestaurantId(@PathVariable Long restaurantId){
        List<MenuItemResponseDto> menuItemDtos = menuItemService.getByRestaurantId(restaurantId);
        return ResponseEntity.ok(menuItemDtos);
    }
}
