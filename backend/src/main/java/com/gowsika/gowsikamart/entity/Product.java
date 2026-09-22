package com.gowsika.gowsikamart.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 100)
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double discountPercent = 0.0;

    @Column(nullable = false)
    private Double finalPrice;

    @Column(nullable = false)
    private Double rating = 4.0;

    @Column(nullable = false)
    private Integer ratingCount = 0;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean bestSeller = false;
    private boolean newArrival = false;
    private boolean trending = false;
    private boolean todayDeal = false;

    @PrePersist
    @PreUpdate
    public void computeFinalPrice() {
        if (price != null && discountPercent != null) {
            this.finalPrice = Math.round((price - (price * discountPercent / 100)) * 100.0) / 100.0;
        }
    }
}
