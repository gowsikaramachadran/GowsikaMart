package com.gowsika.gowsikamart.controller;

import com.gowsika.gowsikamart.dto.OrderStatusUpdateRequest;
import com.gowsika.gowsikamart.entity.Order;
import com.gowsika.gowsikamart.entity.Product;
import com.gowsika.gowsikamart.entity.User;
import com.gowsika.gowsikamart.repository.OrderRepository;
import com.gowsika.gowsikamart.repository.ProductRepository;
import com.gowsika.gowsikamart.repository.UserRepository;
import com.gowsika.gowsikamart.service.OrderService;
import com.gowsika.gowsikamart.service.ProductService;
import com.gowsika.gowsikamart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All endpoints here require ROLE_ADMIN (enforced in SecurityConfig).
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public AdminController(ProductService productService, OrderService orderService, UserService userService,
                            ProductRepository productRepository, OrderRepository orderRepository,
                            UserRepository userRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // ---------- Dashboard ----------
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productRepository.count());
        stats.put("totalUsers", userRepository.count());
        stats.put("totalOrders", orderRepository.count());
        double totalSales = orderRepository.findAll().stream()
                .mapToDouble(Order::getTotalAmount).sum();
        stats.put("totalSales", totalSales);
        return ResponseEntity.ok(stats);
    }

    // ---------- Product Management ----------
    @GetMapping("/products")
    public ResponseEntity<List<Product>> allProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PutMapping("/products/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(productService.updateStock(id, body.get("quantity")));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Order Management ----------
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> allOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> orderDetails(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request.getStatus()));
    }

    // ---------- User Management ----------
    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> userDetails(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<Void> setUserStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        userService.setActive(id, body.get("active"));
        return ResponseEntity.noContent().build();
    }
}
