package com.FoodDash.FoodDash.service;

import com.FoodDash.FoodDash.Dto.CartDto;
import com.FoodDash.FoodDash.Dto.CartItemDto;
import com.FoodDash.FoodDash.entities.Cart;
import com.FoodDash.FoodDash.entities.CartItem;
import com.FoodDash.FoodDash.entities.MenuItem;
import com.FoodDash.FoodDash.entities.User;
import com.FoodDash.FoodDash.repository.CartRepo;
import com.FoodDash.FoodDash.repository.MenuItemRepo;
import com.FoodDash.FoodDash.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartSerivce {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final MenuItemRepo menuItemRepo;


    public CartDto addtoCart(Long userId, Long menuItemId, int quantity) {

         User user = userRepo.findById(userId)
                 .orElseThrow(() -> new RuntimeException("User Not Found"));

        MenuItem menuItem = menuItemRepo.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Item Not found"));

        Cart cart =  cartRepo.findByUserId(userId)
                .orElseGet(()-> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>());
                    newCart.setTotalPrice(0.0);
                    return newCart;
                });

        Optional<CartItem> existingItem = cart.getItems()
                .stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                .findFirst();

        if(existingItem.isPresent()){
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity()+quantity);
        }
        else{
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setMenuItem(menuItem);
            newItem.setQuantity(quantity);

            cart.getItems().add(newItem);
        }

        //Calculate price
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getMenuItem().getPrice()* item.getQuantity())
                .sum();

        cart.setTotalPrice(totalPrice);

        Cart savedCart = cartRepo.save(cart);

        return convertDto(savedCart);
    }

    //

    public CartDto getCart(Long userId) {
        Cart cart = cartRepo.findByUserId(userId).
                orElseThrow(() -> new RuntimeException("User Don't Have Cart"));

        return convertDto(cart);
    }

    //

    public void removeItem(Long userId, Long menuItemId) {

        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("cart not found"));

        cart.getItems().removeIf(item -> item.getMenuItem().getId().equals(menuItemId));

        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getMenuItem().getPrice()*item.getQuantity())
                .sum();

        cart.setTotalPrice(total);

        Cart savedCart = cartRepo.save(cart);
    }

    //

    public void clearCart(Long userId) {

        Cart cart = cartRepo.findByUserId(userId).
                orElseThrow(()-> new RuntimeException("Cart Not Found"));

        cart.getItems().clear();
        cart.setTotalPrice(0.0);

        cartRepo.save(cart);
    }

    //

    private CartDto convertDto(Cart savedCart) {

        List<CartItemDto> items = new ArrayList<>();
        List<CartItem> itemList = savedCart.getItems();

        for(CartItem i : itemList){
            CartItemDto tempItems = new CartItemDto();
            tempItems.setName(i.getMenuItem().getName());
            tempItems.setMenuItemId(i.getMenuItem().getId());
            tempItems.setPrice(i.getMenuItem().getPrice());
            tempItems.setQuantity(i.getQuantity());

            items.add(tempItems);
        }

        CartDto cartDto = new CartDto();
        cartDto.setUserId(savedCart.getUser().getId());
        cartDto.setPrice(savedCart.getTotalPrice());
        cartDto.setItems(items);

        return cartDto;
    }
}
