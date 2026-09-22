package com.gowsika.gowsikamart.service;

import com.gowsika.gowsikamart.entity.Product;
import com.gowsika.gowsikamart.exception.ResourceNotFoundException;
import com.gowsika.gowsikamart.repository.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public List<Product> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> search(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(keyword, keyword);
    }

    public List<Product> getBestSellers() {
        return productRepository.findByBestSellerTrue();
    }

    public List<Product> getNewArrivals() {
        return productRepository.findByNewArrivalTrue();
    }

    public List<Product> getTrending() {
        return productRepository.findByTrendingTrue();
    }

    public List<Product> getTodayDeals() {
        return productRepository.findByTodayDealTrue();
    }

    public List<Product> filter(Long categoryId, String brand, Double minPrice, Double maxPrice,
                                 Double minRating, Boolean inStock, String sortBy) {

        Specification<Product> spec = Specification.where(null);

        if (categoryId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId));
        }
        if (brand != null && !brand.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("brand")), brand.toLowerCase()));
        }
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("finalPrice"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("finalPrice"), maxPrice));
        }
        if (minRating != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("rating"), minRating));
        }
        if (Boolean.TRUE.equals(inStock)) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("stockQuantity"), 0));
        }

        List<Product> results = productRepository.findAll(spec);

        if (sortBy != null) {
            switch (sortBy) {
                case "price_low_high" -> results = results.stream()
                        .sorted(Comparator.comparing(Product::getFinalPrice)).collect(Collectors.toList());
                case "price_high_low" -> results = results.stream()
                        .sorted(Comparator.comparing(Product::getFinalPrice).reversed()).collect(Collectors.toList());
                case "rating" -> results = results.stream()
                        .sorted(Comparator.comparing(Product::getRating).reversed()).collect(Collectors.toList());
                case "newest" -> results = results.stream()
                        .sorted(Comparator.comparing(Product::getCreatedAt).reversed()).collect(Collectors.toList());
                case "discount" -> results = results.stream()
                        .sorted(Comparator.comparing(Product::getDiscountPercent).reversed()).collect(Collectors.toList());
                default -> {}
            }
        }
        return results;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updated) {
        Product product = getProductById(id);
        product.setName(updated.getName());
        product.setBrand(updated.getBrand());
        product.setCategory(updated.getCategory());
        product.setPrice(updated.getPrice());
        product.setDiscountPercent(updated.getDiscountPercent());
        product.setStockQuantity(updated.getStockQuantity());
        product.setDescription(updated.getDescription());
        product.setImageUrl(updated.getImageUrl());
        product.setBestSeller(updated.isBestSeller());
        product.setNewArrival(updated.isNewArrival());
        product.setTrending(updated.isTrending());
        product.setTodayDeal(updated.isTodayDeal());
        return productRepository.save(product);
    }

    public Product updateStock(Long id, Integer quantity) {
        Product product = getProductById(id);
        product.setStockQuantity(quantity);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
