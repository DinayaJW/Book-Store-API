/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resources;

import com.bookstore.model.Customer;
import com.bookstore.service.DataService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Resource class for handling customer-related operations
 * Exposes RESTful endpoints for CRUD operations on customers
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    // Data service instance
    private final DataService dataService = DataService.getInstance();
    
    /**
     * Create a new customer
     * 
     * @param customer The customer to create
     * @return Response with the created customer and status 201 (Created)
     */
    @POST
    public Response createCustomer(Customer customer) {
        Customer createdCustomer = dataService.createCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(createdCustomer).build();
    }
    
    /**
     * Get all customers
     * 
     * @return Response with the list of all customers
     */
    @GET
    public Response getAllCustomers() {
        List<Customer> customers = dataService.getAllCustomers();
        return Response.ok(customers).build();
    }
    
    /**
     * Get a customer by ID
     * 
     * @param id The customer ID
     * @return Response with the customer
     */
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Long id) {
        Customer customer = dataService.getCustomerById(id);
        return Response.ok(customer).build();
    }
    
    /**
     * Update a customer
     * 
     * @param id The customer ID
     * @param customer The updated customer data
     * @return Response with the updated customer
     */
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Long id, Customer customer) {
        Customer updatedCustomer = dataService.updateCustomer(id, customer);
        return Response.ok(updatedCustomer).build();
    }
    
    /**
     * Delete a customer
     * 
     * @param id The customer ID
     * @return Response with status 204 (No Content)
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        dataService.deleteCustomer(id);
        return Response.noContent().build();
    }
}
