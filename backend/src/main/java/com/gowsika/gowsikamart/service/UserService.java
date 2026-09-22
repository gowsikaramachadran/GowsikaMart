package com.gowsika.gowsikamart.service;

import com.gowsika.gowsikamart.entity.User;
import com.gowsika.gowsikamart.exception.ResourceNotFoundException;
import com.gowsika.gowsikamart.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateProfile(Long id, String fullName, String phone) {
        User user = getById(id);
        user.setFullName(fullName);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    public void changePassword(Long id, String currentPassword, String newPassword) {
        User user = getById(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new com.gowsika.gowsikamart.exception.BadRequestException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void setActive(Long id, boolean active) {
        User user = getById(id);
        user.setActive(active);
        userRepository.save(user);
    }
}
