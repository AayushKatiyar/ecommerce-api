package com.aayush.ecommerce.controller;

import com.aayush.ecommerce.entity.Product;
import com.aayush.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 🔥 ADMIN ONLY
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // 🔥 USER + ADMIN
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}