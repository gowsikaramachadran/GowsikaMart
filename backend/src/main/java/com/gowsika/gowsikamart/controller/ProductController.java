package com.gowsika.gowsikamart.controller;

import com.gowsika.gowsikamart.entity.Product;
import com.gowsika.gowsikamart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.search(keyword));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> byCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getByCategory(categoryId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filter(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(productService.filter(categoryId, brand, minPrice, maxPrice, minRating, inStock, sortBy));
    }

    @GetMapping("/best-sellers")
    public ResponseEntity<List<Product>> bestSellers() {
        return ResponseEntity.ok(productService.getBestSellers());
    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<List<Product>> newArrivals() {
        return ResponseEntity.ok(productService.getNewArrivals());
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Product>> trending() {
        return ResponseEntity.ok(productService.getTrending());
    }

    @GetMapping("/today-deals")
    public ResponseEntity<List<Product>> todayDeals() {
        return ResponseEntity.ok(productService.getTodayDeals());
    }

    // Admin-protected create/update/delete are also exposed under /api/admin/products (AdminController)
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
