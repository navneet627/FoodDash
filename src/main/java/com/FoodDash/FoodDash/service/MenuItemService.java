package com.FoodDash.FoodDash.service;

import com.FoodDash.FoodDash.Dto.MenuItemDto;
import com.FoodDash.FoodDash.entities.MenuItem;
import com.FoodDash.FoodDash.entities.Restaurant;
import com.FoodDash.FoodDash.repository.MenuItemRepo;
import com.FoodDash.FoodDash.repository.RestaurantRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepo menuItemRepo;
    private final RestaurantRepo restaurantRepo;

    public MenuItemDto create(MenuItemDto dto) {
        Restaurant restaurant = restaurantRepo.findById(dto.getRestaurantId()).orElseThrow(()->
        new RuntimeException("Restaurant not found with id: " + dto.getRestaurantId()));

        MenuItem menuItem = new MenuItem();
        menuItem.setId(dto.getId());
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setRestaurant(restaurant);

        MenuItem savedMenuItem = menuItemRepo.save(menuItem);

        return convertToDto(savedMenuItem);
    }

    private MenuItemDto convertToDto(MenuItem savedMenuItem) {
        MenuItemDto dto = new MenuItemDto();
        dto.setId(savedMenuItem.getId());
        dto.setName(savedMenuItem.getName());
        dto.setDescription(savedMenuItem.getDescription());
        dto.setPrice(savedMenuItem.getPrice());
        dto.setRestaurantId(savedMenuItem.getRestaurant().getId());
        return dto;
    }
}
