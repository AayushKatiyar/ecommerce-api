package com.aayush.ecommerce.controller;

import com.aayush.ecommerce.entity.Order;
import com.aayush.ecommerce.entity.User;
import com.aayush.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public Order checkout(@RequestParam Long cartId) {
        return orderService.checkout(cartId);
    }

    @GetMapping("/my-orders")
    public List<Order> getMyOrders(Authentication authentication) {

        String email = authentication.getName();

        return orderService.getOrdersByEmail(email);
    }
}