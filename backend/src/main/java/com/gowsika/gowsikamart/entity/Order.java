package com.gowsika.gowsikamart.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double discountAmount = 0.0;

    @Column(nullable = false)
    private Double deliveryCharge = 0.0;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false, length = 20)
    private String status = "PLACED"; // PLACED, CONFIRMED, PACKED, SHIPPED, DELIVERED, CANCELLED

    @Column(nullable = false, length = 30)
    private String paymentMethod = "COD"; // COD, CARD, UPI (mock)

    @Column(length = 100)
    private String customerName;

    @Column(length = 150)
    private String customerEmail;

    @Column(length = 15)
    private String customerPhone;

    @Column(length = 255)
    private String shippingAddress;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 10)
    private String pincode;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
