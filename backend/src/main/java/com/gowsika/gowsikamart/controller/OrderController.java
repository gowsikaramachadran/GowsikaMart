package com.gowsika.gowsikamart.controller;

import com.gowsika.gowsikamart.dto.CheckoutRequest;
import com.gowsika.gowsikamart.entity.Order;
import com.gowsika.gowsikamart.entity.User;
import com.gowsika.gowsikamart.service.OrderService;
import com.gowsika.gowsikamart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(Authentication auth, @RequestBody CheckoutRequest request) {
        User user = userService.getByEmail(auth.getName());
        return ResponseEntity.ok(orderService.placeOrder(user, request));
    }

    @GetMapping
    public ResponseEntity<List<Order>> myOrders(Authentication auth) {
        User user = userService.getByEmail(auth.getName());
        return ResponseEntity.ok(orderService.getOrdersForUser(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
