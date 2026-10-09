package com.library.service;

import com.library.dao.UserDAO;
import com.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthService — handles login authentication.
 *
 * Login logic:
 *  1. Find user by email
 *  2. Compare passwords (plain text for this college project)
 *  3. Return User if valid, or null if invalid
 *
 * NOTE: This project does not use Spring Security or password hashing
 * in order to keep it simple and easy to explain in a college viva.
 */
@Service
public class AuthService {

    @Autowired
    private UserDAO userDAO;

    /**
     * Authenticate a user.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return the User object if credentials are correct, null otherwise
     */
    @Transactional
    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);

        if (user == null) {
            return null;   // Email not found
        }

        if (!user.getPassword().equals(password)) {
            return null;   // Wrong password
        }

        return user;   // Login successful
    }
}
