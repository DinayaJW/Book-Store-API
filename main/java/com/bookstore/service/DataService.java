// DataService.java
package com.bookstore.service;

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.exception.OrderNotFoundException;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.model.Author;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.model.Customer;
import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Service class that simulates database operations using in-memory data structures
 * Provides methods to manage books, authors, customers, carts, and orders
 */
public class DataService {
    // Singleton instance
    private static final DataService instance = new DataService();
    
    // Atomic counters for generating unique IDs
    private final AtomicLong bookIdCounter = new AtomicLong(1);
    private final AtomicLong authorIdCounter = new AtomicLong(1);
    private final AtomicLong customerIdCounter = new AtomicLong(1);
    private final AtomicLong orderIdCounter = new AtomicLong(1);
    
    // In-memory data storage using Maps
    private final Map<Long, Book> books = new HashMap<>();
    private final Map<Long, Author> authors = new HashMap<>();
    private final Map<Long, Customer> customers = new HashMap<>();
    private final Map<Long, Cart> carts = new HashMap<>();
    private final Map<Long, List<Order>> customerOrders = new HashMap<>();
    
    // Private constructor for singleton pattern
    private DataService() {
        // Initialize with some sample data
        initSampleData();
    }
    
    // Initialize sample data
    private void initSampleData() {
        // Create sample authors
        Author author1 = new Author(authorIdCounter.getAndIncrement(), "J.K. Rowling", 
                "British author best known for the Harry Potter series.");
        Author author2 = new Author(authorIdCounter.getAndIncrement(), "George Orwell", 
                "English novelist, essayist, and critic.");
        authors.put(author1.getId(), author1);
        authors.put(author2.getId(), author2);
        
        // Create sample books
        Book book1 = new Book(bookIdCounter.getAndIncrement(), "Harry Potter and the Philosopher's Stone", 
                author1.getId(), "978-0-7475-3269-9", 1997, 15.99, 100);
        Book book2 = new Book(bookIdCounter.getAndIncrement(), "Harry Potter and the Chamber of Secrets", 
                author1.getId(), "978-0-7475-3849-9", 1998, 16.99, 85);
        Book book3 = new Book(bookIdCounter.getAndIncrement(), "1984", 
                author2.getId(), "978-0-451-52493-5", 1949, 12.99, 50);
        books.put(book1.getId(), book1);
        books.put(book2.getId(), book2);
        books.put(book3.getId(), book3);
        
        // Create sample customers
        Customer customer1 = new Customer(customerIdCounter.getAndIncrement(), "John Doe", 
                "john.doe@example.com", "password123");
        customers.put(customer1.getId(), customer1);
        
        // Create empty cart for the customer
        carts.put(customer1.getId(), new Cart(customer1.getId()));
        
        // Initialize empty order list for the customer
        customerOrders.put(customer1.getId(), new ArrayList<>());
    }
    
    // Get singleton instance
    public static DataService getInstance() {
        return instance;
    }
    
    // Book-related methods
    
    /**
     * Create a new book
     * 
     * @param book The book to create
     * @return The created book with generated ID
     * @throws AuthorNotFoundException if the author doesn't exist
     * @throws InvalidInputException if the input is invalid
     */
    public Book createBook(Book book) {
        // Validate book
        validateBook(book);
        
        // Check if author exists
        if (!authors.containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException(book.getAuthorId());
        }
        
        // Generate ID for new book
        book.setId(bookIdCounter.getAndIncrement());
        
        // Add to the collection
        books.put(book.getId(), book);
        
        return book;
    }
    
    /**
     * Get all books
     * 
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    /**
     * Get a book by ID
     * 
     * @param id The book ID
     * @return The book
     * @throws BookNotFoundException if the book doesn't exist
     */
    public Book getBookById(Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }
    
    /**
     * Update an existing book
     * 
     * @param id The book ID
     * @param book The updated book data
     * @return The updated book
     * @throws BookNotFoundException if the book doesn't exist
     * @throws AuthorNotFoundException if the author doesn't exist
     * @throws InvalidInputException if the input is invalid
     */
    public Book updateBook(Long id, Book book) {
        // Check if book exists
        if (!books.containsKey(id)) {
            throw new BookNotFoundException(id);
        }
        
        // Validate book
        validateBook(book);
        
        // Check if author exists
        if (!authors.containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException(book.getAuthorId());
        }
        
        // Update the book
        book.setId(id);
        books.put(id, book);
        
        return book;
    }
    
    /**
     * Delete a book by ID
     * 
     * @param id The book ID
     * @throws BookNotFoundException if the book doesn't exist
     */
    public void deleteBook(Long id) {
        // Check if book exists
        if (!books.containsKey(id)) {
            throw new BookNotFoundException(id);
        }
        
        // Remove the book
        books.remove(id);
    }
    
    /**
     * Get books by author ID
     * 
     * @param authorId The author ID
     * @return List of books by the author
     * @throws AuthorNotFoundException if the author doesn't exist
     */
    public List<Book> getBooksByAuthor(Long authorId) {
        // Check if author exists
        if (!authors.containsKey(authorId)) {
            throw new AuthorNotFoundException(authorId);
        }
        
        // Filter books by author ID
        return books.values().stream()
                .filter(book -> book.getAuthorId().equals(authorId))
                .collect(Collectors.toList());
    }
    
    /**
     * Validate book data
     * 
     * @param book The book to validate
     * @throws InvalidInputException if the input is invalid
     */
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty.");
        }
        
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new InvalidInputException("Book ISBN cannot be empty.");
        }
        
        if (book.getPublicationYear() > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new InvalidInputException("Publication year cannot be in the future.");
        }
        
        if (book.getPrice() <= 0) {
            throw new InvalidInputException("Book price must be greater than zero.");
        }
        
        if (book.getStock() < 0) {
            throw new InvalidInputException("Book stock cannot be negative.");
        }
    }
    
    // Author-related methods
    
    /**
     * Create a new author
     * 
     * @param author The author to create
     * @return The created author with generated ID
     * @throws InvalidInputException if the input is invalid
     */
  public Author createAuthor(Author author) {
    if (author == null) {
        throw new InvalidInputException("Author cannot be null.");
    }

    // Validate author fields
    validateAuthor(author);

    // If client provided an ID and it's not already taken, use it
    if (author.getId() != null) {
        if (authors.containsKey(author.getId())) {
            throw new InvalidInputException("Author ID already exists.");
        }
    } else {
        // Otherwise, generate a new ID
        long newAuthorId = authorIdCounter.getAndIncrement();
        author.setId(newAuthorId);
    }

    // Store the author
    authors.put(author.getId(), author);

    return author;
}

    /**
     * Get all authors
     * 
     * @return List of all authors
     */
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }
    
    /**
     * Get an author by ID
     * 
     * @param id The author ID
     * @return The author
     * @throws AuthorNotFoundException if the author doesn't exist
     */
    public Author getAuthorById(Long id) {
        Author author = authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return author;
    }
    
    /**
     * Update an existing author
     * 
     * @param id The author ID
     * @param author The updated author data
     * @return The updated author
     * @throws AuthorNotFoundException if the author doesn't exist
     * @throws InvalidInputException if the input is invalid
     */
    public Author updateAuthor(Long id, Author author) {
        // Check if author exists
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException(id);
        }
        
        // Validate author
        validateAuthor(author);
        
        // Update the author
        author.setId(id);
        authors.put(id, author);
        
        return author;
    }
    
    /**
     * Delete an author by ID
     * 
     * @param id The author ID
     * @throws AuthorNotFoundException if the author doesn't exist
     * @throws InvalidInputException if author has books
     */
    public void deleteAuthor(Long id) {
        // Check if author exists
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException(id);
        }
        
        // Check if author has books
        boolean hasBooks = books.values().stream()
                .anyMatch(book -> book.getAuthorId().equals(id));
        
        if (hasBooks) {
            throw new InvalidInputException("Cannot delete author with existing books.");
        }
        
        // Remove the author
        authors.remove(id);
    }
    
    /**
     * Validate author data
     * 
     * @param author The author to validate
     * @throws InvalidInputException if the input is invalid
     */
    private void validateAuthor(Author author) {
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty.");
        }
    }
    
    // Customer-related methods
    
    /**
     * Create a new customer
     * 
     * @param customer The customer to create
     * @return The created customer with generated ID
     * @throws InvalidInputException if the input is invalid
     */
    public Customer createCustomer(Customer customer) {
        // Validate customer
        validateCustomer(customer);
        
        // Check if email is already in use
        boolean emailExists = customers.values().stream()
                .anyMatch(c -> c.getEmail().equals(customer.getEmail()));
        
        if (emailExists) {
            throw new InvalidInputException("Email address is already in use.");
        }
        
        // Generate ID for new customer
        customer.setId(customerIdCounter.getAndIncrement());
        
        // Add to the collection
        customers.put(customer.getId(), customer);
        
        // Create empty cart for the customer
        carts.put(customer.getId(), new Cart(customer.getId()));
        
        // Initialize empty order list for the customer
        customerOrders.put(customer.getId(), new ArrayList<>());
        
        return customer;
    }
    
    /**
     * Get all customers
     * 
     * @return List of all customers
     */
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    /**
     * Get a customer by ID
     * 
     * @param id The customer ID
     * @return The customer
     * @throws CustomerNotFoundException if the customer doesn't exist
     */
    public Customer getCustomerById(Long id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }
    
    /**
     * Update an existing customer
     * 
     * @param id The customer ID
     * @param customer The updated customer data
     * @return The updated customer
     * @throws CustomerNotFoundException if the customer doesn't exist
     * @throws InvalidInputException if the input is invalid
     */
    public Customer updateCustomer(Long id, Customer customer) {
        // Check if customer exists
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        
        // Validate customer
        validateCustomer(customer);
        
        // Check if email is already in use by another customer
        boolean emailExists = customers.values().stream()
                .anyMatch(c -> c.getEmail().equals(customer.getEmail()) && !c.getId().equals(id));
        
        if (emailExists) {
            throw new InvalidInputException("Email address is already in use by another customer.");
        }
        
        // Update the customer
        customer.setId(id);
        customers.put(id, customer);
        
        return customer;
    }
    
    /**
     * Delete a customer by ID
     * 
     * @param id The customer ID
     * @throws CustomerNotFoundException if the customer doesn't exist
     */
    public void deleteCustomer(Long id) {
        // Check if customer exists
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        
        // Remove the customer
        customers.remove(id);
        
        // Remove customer's cart
        carts.remove(id);
        
        // Remove customer's orders
        customerOrders.remove(id);
    }
    
    /**
     * Validate customer data
     * 
     * @param customer The customer to validate
     * @throws InvalidInputException if the input is invalid
     */
    private void validateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty.");
        }
        
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("Customer email cannot be empty.");
        }
        
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            throw new InvalidInputException("Customer password cannot be empty.");
        }
    }
    
    // Cart-related methods
    
    /**
     * Get a customer's cart
     * 
     * @param customerId The customer ID
     * @return The customer's cart
     * @throws CustomerNotFoundException if the customer doesn't exist
     * @throws CartNotFoundException if the cart doesn't exist
     */
    public Cart getCart(Long customerId) {
        // Check if customer exists
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get cart
        Cart cart = carts.get(customerId);
        if (cart == null) {
            // Create cart if it doesn't exist
            cart = new Cart(customerId);
            carts.put(customerId, cart);
        }
        
        return cart;
    }
    
    /**
     * Add an item to a customer's cart
     * 
     * @param customerId The customer ID
     * @param cartItem The item to add
     * @return The updated cart
     * @throws CustomerNotFoundException if the customer doesn't exist
     * @throws BookNotFoundException if the book doesn't exist
     * @throws OutOfStockException if the book is out of stock
     */
    public Cart addCartItem(Long customerId, CartItem cartItem) {
        // Check if customer exists
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Check if book exists
        Book book = getBookById(cartItem.getBookId());
        
        // Check stock availability
        if (book.getStock() < cartItem.getQuantity()) {
            throw new OutOfStockException(book.getId(), cartItem.getQuantity(), book.getStock());
        }
        
        // Get cart
        Cart cart = getCart(customerId);
        
        // Add item to cart
        cart.addItem(cartItem);
        
        return cart;
    }
    
    /**
     * Update an item in a customer's cart
     * 
     * @param customerId The customer ID
     * @param bookId The book ID
     * @param quantity The new quantity
     * @return The updated cart
     * @throws CustomerNotFoundException if the customer doesn't exist
     * @throws BookNotFoundException if the book doesn't exist
     * @throws InvalidInputException if the quantity is invalid
     * @throws OutOfStockException if the book is out of stock
     */
    public Cart updateCartItem(Long customerId, Long bookId, int quantity) {
        // Check if customer exists
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Check if book exists
        Book book = getBookById(bookId);
        
        // Validate quantity
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        
        // Check stock availability
        if (book.getStock() < quantity) {
            throw new OutOfStockException(book.getId(), quantity, book.getStock());
        }
        
        // Get cart
        Cart cart = getCart(customerId);
        
        // Check if item exists in cart
        boolean itemExists = cart.getItems().stream()
                .anyMatch(item -> item.getBookId().equals(bookId));
        
        if (!itemExists) {
            throw new InvalidInputException("Book with ID " + bookId + " not found in cart.");
        }
        
        // Update item in cart
        cart.updateItem(bookId, quantity);
        
        return cart;
    }
    
    /**
     * Remove an item from a customer's cart
     * 
     * @param customerId The customer ID
     * @param bookId The book ID
     * @return The updated cart
     * @throws CustomerNotFoundException if the customer doesn't exist
     */
    public Cart removeCartItem(Long customerId, Long bookId) {
        // Check if customer exists
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get cart
        Cart cart = getCart(customerId);
        
        // Remove item from cart
        cart.removeItem(bookId);
        
        return cart;
    }
    
    // Order-related methods
    
    /**
     * Create an order from a customer's cart
     * 
     * @param customerId The customer ID
     * @return The created order
     * @throws CustomerNotFoundException if the customer doesn't exist
     * @throws CartNotFoundException if the cart doesn't exist or is empty
     * @throws OutOfStockException if any book is out of stock
     */
    public Order createOrder(Long customerId) {
        // Check if customer exists
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get cart
        Cart cart = getCart(customerId);
        
        // Check if cart is empty
        if (cart.getItems().isEmpty()) {
            throw new InvalidInputException("Cannot create an order with an empty cart.");
        }
        
        // Create order items and calculate total
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;
        
        for (CartItem cartItem : cart.getItems()) {
            // Get book
            Book book = getBookById(cartItem.getBookId());
            
            // Check stock availability
            if (book.getStock() < cartItem.getQuantity()) {
                throw new OutOfStockException(book.getId(), cartItem.getQuantity(), book.getStock());
            }
            
            // Create order item
            OrderItem orderItem = new OrderItem(
                    book.getId(),
                    book.getTitle(),
                    cartItem.getQuantity(),
                    book.getPrice()
            );
            
            // Add to order items
            orderItems.add(orderItem);
            
            // Add to total
            totalAmount += orderItem.getTotalPrice();
            
            // Update book stock
            book.setStock(book.getStock() - cartItem.getQuantity());
        }
        
        // Create order
        Order order = new Order(
                orderIdCounter.getAndIncrement(),
                customerId,
                orderItems,
                totalAmount
        );
        
        // Add to customer's orders
        List<Order> orders = customerOrders.get(customerId);
        orders.add(order);
        
        // Clear customer's cart
        cart.getItems().clear();
        
        return order;
    }
    
    /**
     * Get all orders for a customer
     * 
     * @param customerId The customer ID
     * @return List of customer's orders
     * @throws CustomerNotFoundException if the customer doesn't exist
     */
    public List<Order> getCustomerOrders(Long customerId) {
        // Check if customer exists
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get customer's orders
        List<Order> orders = customerOrders.get(customerId);
        if (orders == null) {
            // Initialize empty order list
            orders = new ArrayList<>();
            customerOrders.put(customerId, orders);
        }
        
        return orders;
    }
    
    /**
     * Get a specific order for a customer
     * 
     * @param customerId The customer ID
     * @param orderId The order ID
     * @return The order
     * @throws CustomerNotFoundException if the customer doesn't exist
     * @throws OrderNotFoundException if the order doesn't exist
     */
    public Order getCustomerOrder(Long customerId, Long orderId) {
        // Check if customer exists
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get customer's orders
        List<Order> orders = getCustomerOrders(customerId);
        
        // Find order
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        
        // Order not found
        throw new OrderNotFoundException(orderId);
    }
}