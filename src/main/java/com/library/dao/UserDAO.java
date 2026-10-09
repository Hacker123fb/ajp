package com.library.dao;

import com.library.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDAO — Data access for the User entity.
 *
 * Uses Hibernate SessionFactory and HQL.
 * No JDBC is used here.
 */
@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Helper: get the current Hibernate session (managed by Spring)
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save a new user to the database.
     */
    public void save(User user) {
        getSession().save(user);
    }

    /**
     * Find a user by their email address.
     * Used during login.
     * HQL: "FROM User WHERE email = :email"
     */
    public User findByEmail(String email) {
        Query<User> query = getSession().createQuery(
                "FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        List<User> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    /**
     * Find a user by their ID.
     */
    public User findById(int id) {
        return getSession().get(User.class, id);
    }

    /**
     * Find all users with role = MEMBER.
     * Used by librarians and admins to manage members.
     * HQL: "FROM User WHERE role = 'MEMBER'"
     */
    public List<User> findAllMembers() {
        Query<User> query = getSession().createQuery(
                "FROM User WHERE role = 'MEMBER'", User.class);
        return query.getResultList();
    }

    /**
     * Update an existing user.
     */
    public void update(User user) {
        getSession().update(user);
    }

    /**
     * Delete a user by ID.
     */
    public void delete(int id) {
        User user = findById(id);
        if (user != null) {
            getSession().delete(user);
        }
    }
}
