package com.gowsika.gowsikamart.controller;

import com.gowsika.gowsikamart.entity.User;
import com.gowsika.gowsikamart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> profile(Authentication auth) {
        return ResponseEntity.ok(userService.getByEmail(auth.getName()));
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(Authentication auth, @RequestBody Map<String, String> body) {
        User user = userService.getByEmail(auth.getName());
        return ResponseEntity.ok(userService.updateProfile(user.getId(), body.get("fullName"), body.get("phone")));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(Authentication auth, @RequestBody Map<String, String> body) {
        User user = userService.getByEmail(auth.getName());
        userService.changePassword(user.getId(), body.get("currentPassword"), body.get("newPassword"));
        return ResponseEntity.ok("Password changed successfully");
    }
}
