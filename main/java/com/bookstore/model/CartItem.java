/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.model;

/**
 * Represents an item in a customer's shopping cart
 * Contains information about the book and quantity
 */
public class CartItem {
    private Long bookId;
    private int quantity;

    // Default constructor
    public CartItem() {
    }

    // Parameterized constructor
    public CartItem(Long bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}