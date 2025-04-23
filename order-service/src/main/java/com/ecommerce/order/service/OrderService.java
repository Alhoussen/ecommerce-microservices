package com.ecommerce.order.service;

import com.ecommerce.order.client.ProductClient;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        // Validate products and calculate total amount
        double totalAmount = order.getItems().stream()
                .mapToDouble(item -> {
                    var product = productClient.getProduct(item.getProductId());
                    item.setPrice(product.price());
                    item.setProductName(product.name());
                    return item.getPrice() * item.getQuantity();
                })
                .sum();
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomer(String customerEmail) {
        return orderRepository.findByCustomerEmail(customerEmail);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
