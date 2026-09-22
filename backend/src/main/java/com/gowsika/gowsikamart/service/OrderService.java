package com.gowsika.gowsikamart.service;

import com.gowsika.gowsikamart.dto.CheckoutRequest;
import com.gowsika.gowsikamart.entity.*;
import com.gowsika.gowsikamart.exception.BadRequestException;
import com.gowsika.gowsikamart.exception.ResourceNotFoundException;
import com.gowsika.gowsikamart.repository.CartItemRepository;
import com.gowsika.gowsikamart.repository.OrderRepository;
import com.gowsika.gowsikamart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private static final double FREE_DELIVERY_THRESHOLD = 499.0;
    private static final double DELIVERY_CHARGE = 40.0;

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository,
                         ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order placeOrder(User user, CheckoutRequest request) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Your cart is empty");
        }

        double subtotal = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = new Order();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new BadRequestException("Insufficient stock for " + product.getName());
            }

            double lineTotal = product.getFinalPrice() * cartItem.getQuantity();
            subtotal += lineTotal;

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getFinalPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }

        double deliveryCharge = subtotal >= FREE_DELIVERY_THRESHOLD ? 0.0 : DELIVERY_CHARGE;

        order.setUser(user);
        order.setSubtotal(Math.round(subtotal * 100.0) / 100.0);
        order.setDeliveryCharge(deliveryCharge);
        order.setDiscountAmount(0.0);
        order.setTotalAmount(Math.round((subtotal + deliveryCharge) * 100.0) / 100.0);
        order.setStatus("PLACED");
        order.setPaymentMethod(request.getPaymentMethod() == null ? "COD" : request.getPaymentMethod());
        order.setCustomerName(request.getFullName());
        order.setCustomerEmail(request.getEmail());
        order.setCustomerPhone(request.getPhone());
        order.setShippingAddress(request.getAddress());
        order.setCity(request.getCity());
        order.setState(request.getState());
        order.setPincode(request.getPincode());
        order.setItems(orderItems);

        Order saved = orderRepository.save(order);
        cartItemRepository.deleteByUserId(user.getId());
        return saved;
    }

    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
