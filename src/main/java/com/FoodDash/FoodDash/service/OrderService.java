package com.FoodDash.FoodDash.service;

import com.FoodDash.FoodDash.Dto.OrderDto;
import com.FoodDash.FoodDash.Dto.OrderItemDto;
import com.FoodDash.FoodDash.entities.Cart;
import com.FoodDash.FoodDash.entities.CartItem;
import com.FoodDash.FoodDash.entities.Order;
import com.FoodDash.FoodDash.entities.OrderItem;
import com.FoodDash.FoodDash.enums.OrderStatus;
import com.FoodDash.FoodDash.repository.CartRepo;
import com.FoodDash.FoodDash.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepo cartRepo;
    private final OrderRepo orderRepo;


    public  OrderDto placeOrder(Long userId) {

        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Cart Not Found"));

        if(cart.getItems().isEmpty()){
             throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PLACED);

        List<OrderItem> orderItemList = new ArrayList<>();

        for(CartItem cartItem: cart.getItems()){
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setMenuItem(cartItem.getMenuItem());
            orderItem.setPrice(cartItem.getMenuItem().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());

            orderItemList.add(orderItem);
        }

        order.setOrderItemList(orderItemList);

        double totalPrice=0.0;

        totalPrice = orderItemList.stream()
                        .mapToDouble(i->i.getPrice()*i.getQuantity())
                        .sum();

        order.setTotalAmount(totalPrice);

        Order saved = orderRepo.save(order);

        //clear Cart
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepo.save(cart);

        return convertToDto(saved);
    }

    private OrderDto convertToDto(Order order) {

        OrderDto orderDto = new OrderDto();

        orderDto.setOrderId(order.getId());
        orderDto.setStatus(order.getOrderStatus());
        orderDto.setTotalPrice(order.getTotalAmount());


        List<OrderItemDto> orderItemDtoList = new ArrayList<>();

        for(OrderItem item: order.getOrderItemList()){
            OrderItemDto orderItemDto = new OrderItemDto();

            orderItemDto.setName(item.getMenuItem().getName());
            orderItemDto.setPrice(item.getPrice());
            orderItemDto.setQuantity(item.getQuantity());

            orderItemDtoList.add(orderItemDto);
        }

        orderDto.setOrderItemList(orderItemDtoList);

        return orderDto;
    }

    public  List<OrderDto>  getAllOrders() {

        List<Order> orders = orderRepo.findAll();
        List<OrderDto> orderDto = new ArrayList<>();

        for(Order order : orders){
            orderDto.add(convertToDto(order));
        }

        return orderDto;
    }
}
