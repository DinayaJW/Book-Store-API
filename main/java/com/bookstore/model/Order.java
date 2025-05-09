package com.bookstore.model;



import java.util.List;
import java.util.ArrayList;
import java.util.Date;



/**
 * Represents an order in the bookstore system
 * Contains information about the order such as customer ID, order items, and timestamp
 */
public class Order {
    private Long id;
    private Long customerId;
    private List<OrderItem> items;
    private Date orderDate;
    private double totalAmount;

    // Default constructor
    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = new Date();
    }

    // Parameterized constructor
    public Order(Long id, Long customerId, List<OrderItem> items, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.orderDate = new Date();
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
