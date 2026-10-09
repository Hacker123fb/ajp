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
     * Add a new member.
     * Always sets role to MEMBER.
     */
    @Transactional
    public void addMember(User user) {
        user.setRole("MEMBER");
        userDAO.save(user);
    }

    /**
     * Get all members (users with role = MEMBER).
     */
    @Transactional(readOnly = true)
    public List<User> getAllMembers() {
        return userDAO.findAllMembers();
    }

    /**
     * Get a single member by ID.
     */
    @Transactional(readOnly = true)
    public User getMemberById(int id) {
        return userDAO.findById(id);
    }

    /**
     * Update member details.
     * Always preserves role as MEMBER.
     */
    @Transactional
    public void updateMember(User user) {
        user.setRole("MEMBER");
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
