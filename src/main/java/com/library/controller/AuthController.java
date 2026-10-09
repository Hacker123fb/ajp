package com.library.controller;

import com.library.entity.User;
import com.library.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

/**
 * AuthController — handles login and logout.
 *
 * URLs:
 *  GET  /auth/login  -> show login form
 *  POST /auth/login  -> process login
 *  GET  /auth/logout -> invalidate session and redirect to login
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Show the login page.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";   // -> WEB-INF/views/login.jsp
    }

    /**
     * Process the login form submission.
     *
     * If credentials are valid:
     *   - Store userId, userName, role in HttpSession
     *   - Redirect to dashboard
     *
     * If credentials are invalid:
     *   - Return to login page with an error message
     */
    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {

        User user = authService.login(email, password);

        if (user != null) {
            // Store user info in session
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole());

            // Redirect to dashboard after successful login
            return "redirect:/dashboard";
        } else {
            // Login failed - return to login page with error
            model.addAttribute("error", "Invalid email or password. Please try again.");
            return "login";
        }
    }

    /**
     * Logout - invalidate the session and redirect to login.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
