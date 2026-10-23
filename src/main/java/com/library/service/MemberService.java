package com.library.service;

import com.library.dao.UserDAO;
import com.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MemberService — business logic for managing library members.
 *
 * Members always have role = "MEMBER".
 */
@Service
public class MemberService {

    @Autowired
    private UserDAO userDAO;

    /**
     * Add a new member or librarian.
     * Defaults to MEMBER if role is not set.
     */
    @Transactional
    public void addMember(User user) {
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("MEMBER");
        }
        userDAO.save(user);
    }

    /**
     * Get all members and librarians.
     */
    @Transactional(readOnly = true)
    public List<User> getAllMembers() {
        return userDAO.findAllMembers();
    }

    /**
     * Get a single user by ID.
     */
    @Transactional(readOnly = true)
    public User getMemberById(int id) {
        return userDAO.findById(id);
    }

    /**
     * Update user details and role.
     * Defaults to MEMBER if role is not set.
     */
    @Transactional
    public void updateMember(User user) {
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("MEMBER");
        }
        userDAO.update(user);
    }

    /**
     * Delete a member by ID.
     */
    @Transactional
    public void deleteMember(int id) {
        userDAO.delete(id);
    }
}
