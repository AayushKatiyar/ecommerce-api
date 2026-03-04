package com.aayush.ecommerce.service;

import com.aayush.ecommerce.entity.*;
import com.aayush.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Cart addToCart(Long userId, Long productId, int quantity) {

        User user = userRepository.findById(userId).orElseThrow();

        Product product = productRepository.findById(productId).orElseThrow();

        Cart cart = cartRepository.findAll().stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .findFirst()
                .orElseGet(() -> {
                    Cart newCart = Cart.builder().user(user).build();
                    return cartRepository.save(newCart);
                });

        CartItem item = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        cartItemRepository.save(item);

        return cart;
    }
}