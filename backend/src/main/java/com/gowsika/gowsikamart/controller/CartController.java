package com.gowsika.gowsikamart.controller;

import com.gowsika.gowsikamart.dto.CartItemRequest;
import com.gowsika.gowsikamart.entity.CartItem;
import com.gowsika.gowsikamart.entity.User;
import com.gowsika.gowsikamart.service.CartService;
import com.gowsika.gowsikamart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    private User currentUser(Authentication auth) {
        return userService.getByEmail(auth.getName());
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(Authentication auth) {
        User user = currentUser(auth);
        return ResponseEntity.ok(cartService.getCart(user.getId()));
    }

    @PostMapping("/items")
    public ResponseEntity<CartItem> addItem(Authentication auth, @RequestBody CartItemRequest request) {
        User user = currentUser(auth);
        return ResponseEntity.ok(cartService.addToCart(user, request.getProductId(), request.getQuantity()));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<CartItem> updateItem(Authentication auth, @PathVariable Long id,
                                                @RequestBody Map<String, Integer> body) {
        User user = currentUser(auth);
        return ResponseEntity.ok(cartService.updateQuantity(user.getId(), id, body.get("quantity")));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItem(Authentication auth, @PathVariable Long id) {
        User user = currentUser(auth);
        cartService.removeItem(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
