package com.FoodDash.FoodDash.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<CartItem> items;
}
