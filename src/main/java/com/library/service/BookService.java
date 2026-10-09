package com.library.service;

import com.library.dao.BookDAO;
import com.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BookService — business logic for book operations.
 *
 * All methods are @Transactional so Hibernate session is managed by Spring.
 */
@Service
public class BookService {

    @Autowired
    private BookDAO bookDAO;

    /**
     * Add a new book.
     * Sets availableQuantity = quantity when adding.
     */
    @Transactional
    public void addBook(Book book) {
        // When adding a new book, available = total quantity
        book.setAvailableQuantity(book.getQuantity());
        bookDAO.save(book);
    }

    /**
     * Get all books.
     */
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    /**
     * Get a single book by ID.
     */
    @Transactional(readOnly = true)
    public Book getBookById(int id) {
        return bookDAO.findById(id);
    }

    /**
     * Search books by title keyword.
     */
    @Transactional(readOnly = true)
    public List<Book> searchBooks(String title) {
        return bookDAO.searchByTitle(title);
    }

    /**
     * Update an existing book.
     */
    @Transactional
    public void updateBook(Book book) {
        bookDAO.update(book);
    }

    /**
     * Delete a book by ID.
     */
    @Transactional
    public void deleteBook(int id) {
        bookDAO.delete(id);
    }
}
