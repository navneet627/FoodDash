package com.FoodDash.FoodDash.controller;


import com.FoodDash.FoodDash.Dto.MenuItemDto;
import com.FoodDash.FoodDash.entities.MenuItem;
import com.FoodDash.FoodDash.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {


    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemDto> createMenuItem(@RequestBody MenuItemDto dto){
         return ResponseEntity.ok(menuItemService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(menuItemService.getByid(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItemDto>> getByRestaurantId(@PathVariable Long restaurantId){
        return ResponseEntity.ok(menuItemService.getByRestaurantId(restaurantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        menuItemService.deleteById(id);
        return ResponseEntity.ok("Menu item Deleted");
    }
}
