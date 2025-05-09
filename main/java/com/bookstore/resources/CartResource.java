/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resources;

import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.service.DataService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource class for handling cart-related operations
 * Exposes RESTful endpoints for managing a customer's shopping cart
 */
@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    // Data service instance
    private final DataService dataService = DataService.getInstance();
    
    /**
     * Get a customer's cart
     * 
     * @param customerId The customer ID
     * @return Response with the customer's cart
     */
    @GET
    public Response getCart(@PathParam("customerId") Long customerId) {
        Cart cart = dataService.getCart(customerId);
        return Response.ok(cart).build();
    }
    
    /**
     * Add an item to a customer's cart
     * 
     * @param customerId The customer ID
     * @param cartItem The item to add
     * @return Response with the updated cart
     */
    @POST
    @Path("/items")
    public Response addCartItem(@PathParam("customerId") Long customerId, CartItem cartItem) {
        Cart updatedCart = dataService.addCartItem(customerId, cartItem);
        return Response.ok(updatedCart).build();
    }
    
    /**
     * Update an item in a customer's cart
     * 
     * @param customerId The customer ID
     * @param bookId The book ID
     * @param cartItem The item with updated quantity
     * @return Response with the updated cart
     */
    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId,
            CartItem cartItem) {
        // Use the quantity from the cart item
        Cart updatedCart = dataService.updateCartItem(customerId, bookId, cartItem.getQuantity());
        return Response.ok(updatedCart).build();
    }
    
    /**
     * Remove an item from a customer's cart
     * 
     * @param customerId The customer ID
     * @param bookId The book ID
     * @return Response with the updated cart
     */
    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId) {
        Cart updatedCart = dataService.removeCartItem(customerId, bookId);
        return Response.ok(updatedCart).build();
    }
}
