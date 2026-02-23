package com.FoodDash.FoodDash.service;

import com.FoodDash.FoodDash.entities.Restaurant;
import com.FoodDash.FoodDash.repository.RestaurantRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {


    private final RestaurantRepo restaurantRepo;


    public List<Restaurant> createRestaurant(List<Restaurant> restaurants) {
        return restaurantRepo.saveAll(restaurants);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepo.findById(id).orElseThrow(() ->
                 new RuntimeException("Restaurant not found with id: " + id));
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant){
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Restaurant not found with id: " + id));

        restaurant.setName(updatedRestaurant.getName());
        restaurant.setRating(updatedRestaurant.getRating());
        restaurant.setLocation(updatedRestaurant.getLocation());


        return restaurantRepo.save(restaurant);
    }

    public void deleteRestaurant(Long id){
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Restaurant not found with id: " + id));
        restaurantRepo.delete(restaurant);
    }
}
