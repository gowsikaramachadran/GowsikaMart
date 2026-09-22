package com.gowsika.gowsikamart.service;

import com.gowsika.gowsikamart.entity.CartItem;
import com.gowsika.gowsikamart.entity.Product;
import com.gowsika.gowsikamart.entity.User;
import com.gowsika.gowsikamart.exception.BadRequestException;
import com.gowsika.gowsikamart.exception.ResourceNotFoundException;
import com.gowsika.gowsikamart.repository.CartItemRepository;
import com.gowsika.gowsikamart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addToCart(User user, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (quantity == null || quantity < 1) quantity = 1;
        if (product.getStockQuantity() < quantity) {
            throw new BadRequestException("Only " + product.getStockQuantity() + " units available in stock");
        }

        CartItem item = cartItemRepository.findByUserIdAndProductId(user.getId(), productId)
                .orElse(new CartItem());

        if (item.getId() == null) {
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(quantity);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
        return cartItemRepository.save(item);
    }

    public CartItem updateQuantity(Long userId, Long cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getUser().getId().equals(userId)) {
            throw new BadRequestException("You cannot modify another user's cart");
        }
        if (quantity < 1) {
            throw new BadRequestException("Quantity must be at least 1");
        }
        if (item.getProduct().getStockQuantity() < quantity) {
            throw new BadRequestException("Only " + item.getProduct().getStockQuantity() + " units available");
        }
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    public void removeItem(Long userId, Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!item.getUser().getId().equals(userId)) {
            throw new BadRequestException("You cannot modify another user's cart");
        }
        cartItemRepository.delete(item);
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
