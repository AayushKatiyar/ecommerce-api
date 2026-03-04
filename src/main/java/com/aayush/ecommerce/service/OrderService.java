package com.aayush.ecommerce.service;

import com.aayush.ecommerce.entity.*;
import com.aayush.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.aayush.ecommerce.repository.UserRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public Order checkout(Long cartId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow();

        Order order = Order.builder()
                .user(cart.getUser())
                .createdAt(LocalDateTime.now())
                .build();

        order = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            product.setStock(product.getStock() - cartItem.getQuantity());

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProduct().getPrice().doubleValue())
                    .build();

            orderItems.add(item);
        }

        orderItemRepository.saveAll(orderItems);

        order.setItems(orderItems);

        /* Clear the cart */
        cart.getItems().clear();
        cartRepository.save(cart);

        return order;
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
    public List<Order> getOrdersByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }
}