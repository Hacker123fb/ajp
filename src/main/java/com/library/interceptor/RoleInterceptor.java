package com.library.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * RoleInterceptor — enforces authentication and role-based access control.
 *
 * Implements Spring MVC HandlerInterceptor.
 * Runs BEFORE every controller method (preHandle).
 *
 * Logic:
 *  1. Check if user is logged in (session has "userId")
 *  2. If not logged in -> redirect to login page
 *  3. Get role from session
 *  4. Check if URL is allowed for that role
 *  5. If allowed  -> return true  (request continues to controller)
 *  6. If not allowed -> redirect to error page
 *
 * URL patterns checked:
 *  /books/add, /books/edit, /books/delete
 *  /members/**
 *  /borrow/issue, /borrow/return
 */
@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        // Step 1: Check if user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return false;
        }

        // Step 2: Get the user's role from session
        String role = (String) session.getAttribute("role");

        // Step 3: Get the requested URL path
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        // Remove context path from URI for easier matching
        String path = uri.substring(contextPath.length());

        // Step 4: Check permissions based on role

        // --- MEMBER restrictions ---
        if ("MEMBER".equals(role)) {
            if (path.startsWith("/books/add") ||
                path.startsWith("/books/edit") ||
                path.startsWith("/books/delete") ||
                path.startsWith("/members") ||
                (path.startsWith("/borrow") && !path.startsWith("/borrow/history"))) {

                response.sendRedirect(contextPath + "/error");
                return false;
            }
        }

        // --- LIBRARIAN restrictions ---
        if ("LIBRARIAN".equals(role)) {
            if (path.startsWith("/books/delete") ||
                path.startsWith("/members")) {

                response.sendRedirect(contextPath + "/error");
                return false;
            }
        }

        // --- ADMIN has full access ---
        // No restrictions for ADMIN

        // Step 5: Access is allowed, continue to controller
        return true;
    }
}
