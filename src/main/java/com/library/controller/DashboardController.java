package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DashboardController — displays the dashboard.
 *
 * The dashboard JSP reads the role from HttpSession
 * and shows role-specific navigation links.
 *
 * URL:
 *  GET /dashboard -> dashboard.jsp
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    /**
     * Show the dashboard page.
     * Role-specific content is handled inside dashboard.jsp using EL + JSTL.
     */
    @GetMapping
    public String showDashboard() {
        return "dashboard";   // -> WEB-INF/views/dashboard.jsp
    }
}
