package com.library.dao;

import com.library.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BookDAO — Data access for the Book entity.
 *
 * Uses Hibernate SessionFactory and HQL.
 * No JDBC is used here.
 */
@Repository
public class BookDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save a new book.
     */
    public void save(Book book) {
        getSession().save(book);
    }

    /**
     * Find a book by its ID.
     */
    public Book findById(int id) {
        return getSession().get(Book.class, id);
    }

    /**
     * Get all books.
     * HQL: "FROM Book"
     */
    public List<Book> findAll() {
        Query<Book> query = getSession().createQuery("FROM Book", Book.class);
        return query.getResultList();
    }

    /**
     * Search books by title (case-insensitive, partial match).
     * HQL: "FROM Book WHERE lower(title) LIKE :keyword"
     */
    public List<Book> searchByTitle(String keyword) {
        Query<Book> query = getSession().createQuery(
                "FROM Book WHERE lower(title) LIKE :keyword", Book.class);
        query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }

    /**
     * Update an existing book.
     */
    public void update(Book book) {
        getSession().update(book);
    }

    /**
     * Delete a book by ID.
     */
    public void delete(int id) {
        Book book = findById(id);
        if (book != null) {
            getSession().delete(book);
        }
    }
}
