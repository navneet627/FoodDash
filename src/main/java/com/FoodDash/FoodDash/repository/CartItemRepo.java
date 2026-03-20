package com.FoodDash.FoodDash.repository;

import com.FoodDash.FoodDash.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long>
{
}
