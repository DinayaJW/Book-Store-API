/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.exception;

import com.bookstore.model.ErrorResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Combined exception mapper class that handles all types of exceptions
 * Maps custom exceptions to appropriate HTTP responses
 */
@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        // Default error response for unexpected exceptions
        ErrorResponse errorResponse = new ErrorResponse(
                "Internal Server Error", 
                exception.getMessage() != null ? exception.getMessage() : "An unexpected error occurred"
        );
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        
        // Determine the appropriate HTTP status and error message based on exception type
        if (exception instanceof BookNotFoundException) {
            errorResponse = new ErrorResponse("Book Not Found", exception.getMessage());
            status = Response.Status.NOT_FOUND;
        }
        else if (exception instanceof AuthorNotFoundException) {
            errorResponse = new ErrorResponse("Author Not Found", exception.getMessage());
            status = Response.Status.NOT_FOUND;
        }
        else if (exception instanceof CustomerNotFoundException) {
            errorResponse = new ErrorResponse("Customer Not Found", exception.getMessage());
            status = Response.Status.NOT_FOUND;
        }
        else if (exception instanceof CartNotFoundException) {
            errorResponse = new ErrorResponse("Cart Not Found", exception.getMessage());
            status = Response.Status.NOT_FOUND;
        }
        else if (exception instanceof OrderNotFoundException) {
            errorResponse = new ErrorResponse("Order Not Found", exception.getMessage());
            status = Response.Status.NOT_FOUND;
        }
        else if (exception instanceof InvalidInputException) {
            errorResponse = new ErrorResponse("Invalid Input", exception.getMessage());
            status = Response.Status.BAD_REQUEST;
        }
        else if (exception instanceof OutOfStockException) {
            errorResponse = new ErrorResponse("Out Of Stock", exception.getMessage());
            status = Response.Status.BAD_REQUEST;
        }
        
        // Return the appropriate response
        return Response.status(status)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
