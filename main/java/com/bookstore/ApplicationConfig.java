package com.bookstore;


import com.bookstore.exception.ExceptionMapper;
import com.bookstore.resources.AuthorResource;
import com.bookstore.resources.BookResource;
import com.bookstore.resources.CartResource;
import com.bookstore.resources.CustomerResource;
import com.bookstore.resources.OrderResource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * JAX-RS Application configuration class
 * Registers all resources and providers (exception mappers)
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    
    /**
     * Get the classes to be included in the JAX-RS application
     * 
     * @return Set of classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        
        // Register resource classes
        resources.add(BookResource.class);
        resources.add(AuthorResource.class);
        resources.add(CustomerResource.class);
        resources.add(CartResource.class);
        resources.add(OrderResource.class);
        
        // Register the combined exception mapper provider
        resources.add(ExceptionMapper.class);
        
        return resources;
    }
}

