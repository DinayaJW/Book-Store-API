/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.exception;

/**
 * Exception thrown when a cart for the specified customer cannot be found
 */
public class CartNotFoundException extends RuntimeException {
    
    public CartNotFoundException(String message) {
        super(message);
    }
    
    public CartNotFoundException(Long customerId) {
        super("Cart for customer with ID " + customerId + " does not exist.");
    }
}
