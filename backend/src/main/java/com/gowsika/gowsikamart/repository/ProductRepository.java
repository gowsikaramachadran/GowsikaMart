package com.gowsika.gowsikamart.repository;

import com.gowsika.gowsikamart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(String name, String brand);
    List<Product> findByBestSellerTrue();
    List<Product> findByNewArrivalTrue();
    List<Product> findByTrendingTrue();
    List<Product> findByTodayDealTrue();
}
