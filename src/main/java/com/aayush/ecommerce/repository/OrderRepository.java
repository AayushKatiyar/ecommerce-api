package com.aayush.ecommerce.repository;

import com.aayush.ecommerce.entity.Order;
import com.aayush.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);


}