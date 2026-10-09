package com.library.controller;

import com.library.entity.User;
import com.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * MemberController — handles library member management.
 *
 * Only ADMIN can access /members/** (enforced by RoleInterceptor).
 *
 * URLs:
 *  GET  /members            -> list all members
 *  GET  /members/add        -> show add member form
 *  POST /members/add        -> save new member
 *  GET  /members/edit/{id}  -> show edit member form
 *  POST /members/edit       -> update member
 *  GET  /members/delete/{id}-> delete member
 */
@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * List all members.
     */
    @GetMapping
    public String listMembers(Model model) {
        List<User> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "members";   // -> WEB-INF/views/members.jsp
    }

    /**
     * Show the Add Member form.
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("member", new User());
        model.addAttribute("formAction", "Add Member");
        return "member-form";   // -> WEB-INF/views/member-form.jsp
    }

    /**
     * Save a new member.
     */
    @PostMapping("/add")
    public String addMember(@ModelAttribute("member") User user) {
        memberService.addMember(user);
        return "redirect:/members";
    }

    /**
     * Show the Edit Member form.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        User member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        model.addAttribute("formAction", "Edit Member");
        return "member-form";   // reuse member-form.jsp
    }

    /**
     * Update an existing member.
     */
    @PostMapping("/edit")
    public String updateMember(@ModelAttribute("member") User user) {
        memberService.updateMember(user);
        return "redirect:/members";
    }

    /**
     * Delete a member.
     */
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable("id") int id) {
        memberService.deleteMember(id);
        return "redirect:/members";
    }
}
