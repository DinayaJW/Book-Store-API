/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resources;

import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.service.DataService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Resource class for handling author-related operations
 * Exposes RESTful endpoints for CRUD operations on authors
 */
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    // Data service instance
    private final DataService dataService = DataService.getInstance();
    
    /**
     * Create a new author
     * 
     * @param author The author to create
     * @return Response with the created author and status 201 (Created)
     */
    @POST
    public Response createAuthor(Author author) {
        Author createdAuthor = dataService.createAuthor(author);
        return Response.status(Response.Status.CREATED).entity(createdAuthor).build();
    }
    
    /**
     * Get all authors
     * 
     * @return Response with the list of all authors
     */
    @GET
    public Response getAllAuthors() {
        List<Author> authors = dataService.getAllAuthors();
        return Response.ok(authors).build();
    }
    
    /**
     * Get an author by ID
     * 
     * @param id The author ID
     * @return Response with the author
     */
    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") Long id) {
        Author author = dataService.getAuthorById(id);
        return Response.ok(author).build();
    }
    
    /**
     * Update an author
     * 
     * @param id The author ID
     * @param author The updated author data
     * @return Response with the updated author
     */
    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") Long id, Author author) {
        Author updatedAuthor = dataService.updateAuthor(id, author);
        return Response.ok(updatedAuthor).build();
    }
    
    /**
     * Delete an author
     * 
     * @param id The author ID
     * @return Response with status 204 (No Content)
     */
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        dataService.deleteAuthor(id);
        return Response.noContent().build();
    }
    
    /**
     * Get all books by an author
     * 
     * @param id The author ID
     * @return Response with the list of books by the author
     */
    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") Long id) {
        List<Book> books = dataService.getBooksByAuthor(id);
        return Response.ok(books).build();
    }
}
