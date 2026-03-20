package com.FoodDash.FoodDash.service;

import com.FoodDash.FoodDash.Dto.MenuItemDto;
import com.FoodDash.FoodDash.entities.MenuItem;
import com.FoodDash.FoodDash.entities.Restaurant;
import com.FoodDash.FoodDash.repository.MenuItemRepo;
import com.FoodDash.FoodDash.repository.RestaurantRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public MenuItemDto getByid(Long id) {
        MenuItem menuItem = menuItemRepo.findById(id).orElseThrow(() ->
                new RuntimeException("MenuItem Not Fount with given ID: "+ id));

        return convertToDto(menuItem);
    }

    public void deleteById(Long id) {
         menuItemRepo.deleteById(id);
    }

    public List<MenuItemDto> getByRestaurantId(Long id) {

        return menuItemRepo.findByRestaurantId(id)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
