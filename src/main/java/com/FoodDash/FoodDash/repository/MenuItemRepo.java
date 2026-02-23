package com.FoodDash.FoodDash.repository;


import com.FoodDash.FoodDash.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);
}
