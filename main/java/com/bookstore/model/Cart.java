/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.bookstore.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a shopping cart in the bookstore system
 * Contains a list of cart items and associated customer ID
 */
public class Cart {
    private Long customerId;
    private List<CartItem> items;

    // Default constructor
    public Cart() {
        this.items = new ArrayList<>();
    }

    // Parameterized constructor
    public Cart(Long customerId) {
        this.customerId = customerId;
        this.items = new ArrayList<>();
    }

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    // Helper methods
    public void addItem(CartItem item) {
        // Check if the item already exists in the cart
        for (CartItem existingItem : items) {
            if (existingItem.getBookId().equals(item.getBookId())) {
                // Update quantity of existing item
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        // Add new item to the cart
        items.add(item);
    }

    public void updateItem(Long bookId, int quantity) {
        for (CartItem item : items) {
            if (item.getBookId().equals(bookId)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public void removeItem(Long bookId) {
        items.removeIf(item -> item.getBookId().equals(bookId));
    }
}