/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resources;

import com.bookstore.model.Order;
import com.bookstore.service.DataService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Resource class for handling order-related operations
 * Exposes RESTful endpoints for managing a customer's orders
 */
@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    // Data service instance
    private final DataService dataService = DataService.getInstance();
    
    /**
     * Create an order from a customer's cart
     * 
     * @param customerId The customer ID
     * @return Response with the created order and status 201 (Created)
     */
    @POST
    public Response createOrder(@PathParam("customerId") Long customerId) {
        Order createdOrder = dataService.createOrder(customerId);
        return Response.status(Response.Status.CREATED).entity(createdOrder).build();
    }
    
    /**
     * Get all orders for a customer
     * 
     * @param customerId The customer ID
     * @return Response with the list of customer's orders
     */
    @GET
    public Response getCustomerOrders(@PathParam("customerId") Long customerId) {
        List<Order> orders = dataService.getCustomerOrders(customerId);
        return Response.ok(orders).build();
    }
    
    /**
     * Get a specific order for a customer
     * 
     * @param customerId The customer ID
     * @param orderId The order ID
     * @return Response with the order
     */
    @GET
    @Path("/{orderId}")
    public Response getCustomerOrder(
            @PathParam("customerId") Long customerId,
            @PathParam("orderId") Long orderId) {
        Order order = dataService.getCustomerOrder(customerId, orderId);
        return Response.ok(order).build();
    }
}
