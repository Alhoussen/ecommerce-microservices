package com.ecommerce.order.client;

public record ProductResponse(Long id, String name, String description, Double price, Integer stock) {}
