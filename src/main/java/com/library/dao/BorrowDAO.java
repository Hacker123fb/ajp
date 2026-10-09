package com.library.dao;

import com.library.entity.BorrowRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BorrowDAO — Data access for the BorrowRecord entity.
 *
 * Uses Hibernate SessionFactory and HQL.
 * No JDBC is used here.
 */
@Repository
public class BorrowDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save a new borrow record (when a book is issued).
     */
    public void save(BorrowRecord record) {
        getSession().save(record);
    }

    /**
     * Find a borrow record by ID.
     */
    public BorrowRecord findById(int id) {
        return getSession().get(BorrowRecord.class, id);
    }

    /**
     * Get all borrow records.
     * Used by ADMIN and LIBRARIAN to see all issued/returned books.
     * HQL: "FROM BorrowRecord"
     */
    public List<BorrowRecord> findAll() {
        Query<BorrowRecord> query = getSession().createQuery(
                "FROM BorrowRecord", BorrowRecord.class);
        return query.getResultList();
    }

    /**
     * Get all borrow records for a specific member.
     * Used to show a member's borrowing history (my-books page).
     * HQL: "FROM BorrowRecord WHERE user.id = :userId"
     */
    public List<BorrowRecord> findByUser(int userId) {
        Query<BorrowRecord> query = getSession().createQuery(
                "FROM BorrowRecord WHERE user.id = :userId", BorrowRecord.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * Update a borrow record (when a book is returned).
     */
    public void update(BorrowRecord record) {
        getSession().update(record);
    }
}
