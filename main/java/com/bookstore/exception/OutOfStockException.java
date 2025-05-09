/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.exception;

/**
 * Exception thrown when a book is out of stock or the requested quantity exceeds available stock
 */
public class OutOfStockException extends RuntimeException {
    
    public OutOfStockException(String message) {
        super(message);
    }
    
    public OutOfStockException(Long bookId, int requestedQuantity, int availableStock) {
        super("Book with ID " + bookId + " has insufficient stock. Requested: " + 
              requestedQuantity + ", Available: " + availableStock);
    }
}