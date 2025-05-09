/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resources;
import com.bookstore.model.Book;
import com.bookstore.service.DataService;


import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Resource class for handling book-related operations
 * Exposes RESTful endpoints for CRUD operations on books
 */
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    // Data service instance
    private final DataService dataService = DataService.getInstance();
    
    /**
     * Create a new book
     * 
     * @param book The book to create
     * @return Response with the created book and status 201 (Created)
     */
    @POST
    public Response createBook(Book book) {
        Book createdBook = dataService.createBook(book);
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }
    
    /**
     * Get all books
     * 
     * @return Response with the list of all books
     */
    @GET
    public Response getAllBooks() {
        List<Book> books = dataService.getAllBooks();
        return Response.ok(books).build();
    }
    
    /**
     * Get a book by ID
     * 
     * @param id The book ID
     * @return Response with the book
     */
    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id) {
        Book book = dataService.getBookById(id);
        return Response.ok(book).build();
    }
    
    /**
     * Update a book
     * 
     * @param id The book ID
     * @param book The updated book data
     * @return Response with the updated book
     */
    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") Long id, Book book) {
        Book updatedBook = dataService.updateBook(id, book);
        return Response.ok(updatedBook).build();
    }
    
    /**
     * Delete a book
     * 
     * @param id The book ID
     * @return Response with status 204 (No Content)
     */
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        dataService.deleteBook(id);
        return Response.noContent().build();
    }
    
}
