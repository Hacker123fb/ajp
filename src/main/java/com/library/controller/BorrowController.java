package com.library.controller;

import com.library.entity.BorrowRecord;
import com.library.entity.User;
import com.library.exception.LibraryException;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * BorrowController — handles book issue and return operations.
 *
 * URLs:
 *  GET  /borrow          -> show borrow page (issue form + all records)
 *  POST /borrow/issue    -> issue a book to a member
 *  GET  /borrow/return/{id} -> return a book
 *  GET  /borrow/history  -> member's own borrowing history
 */
@Controller
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    /**
     * Show the borrow management page.
     * Displays the issue form and all borrow records.
     * Only accessible to ADMIN and LIBRARIAN (enforced by interceptor).
     */
    @GetMapping
    public String showBorrowPage(Model model) {
        List<BorrowRecord> records = borrowService.getAllBorrowRecords();
        List<User> members = memberService.getAllMembers();

        model.addAttribute("records", records);
        model.addAttribute("members", members);
        model.addAttribute("books", bookService.getAllBooks());

        return "borrow";   // -> WEB-INF/views/borrow.jsp
    }

    /**
     * Issue a book to a member.
     * Called from the issue form on borrow.jsp.
     */
    @PostMapping("/issue")
    public String issueBook(@RequestParam("memberId") int memberId,
                            @RequestParam("bookId") int bookId,
                            @RequestParam("dueDate") String dueDateStr,
                            Model model) {

        try {
            Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr);
            borrowService.issueBook(memberId, bookId, dueDate);
            return "redirect:/borrow";

        } catch (LibraryException e) {
            model.addAttribute("error", e.getMessage());
            // Reload the page with the error
            model.addAttribute("records", borrowService.getAllBorrowRecords());
            model.addAttribute("members", memberService.getAllMembers());
            model.addAttribute("books", bookService.getAllBooks());
            return "borrow";

        } catch (ParseException e) {
            model.addAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
            model.addAttribute("records", borrowService.getAllBorrowRecords());
            model.addAttribute("members", memberService.getAllMembers());
            model.addAttribute("books", bookService.getAllBooks());
            return "borrow";
        }
    }

    /**
     * Return a book.
     * Updates the BorrowRecord and calculates fine if applicable.
     */
    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable("id") int borrowId,
                             Model model) {
        try {
            borrowService.returnBook(borrowId);
            return "redirect:/borrow";

        } catch (LibraryException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("records", borrowService.getAllBorrowRecords());
            model.addAttribute("members", memberService.getAllMembers());
            model.addAttribute("books", bookService.getAllBooks());
            return "borrow";
        }
    }

    /**
     * Show the logged-in member's own borrowing history.
     * Accessible to MEMBER, LIBRARIAN, and ADMIN.
     */
    @GetMapping("/history")
    public String memberHistory(HttpSession session, Model model) {
        int userId = (int) session.getAttribute("userId");
        List<BorrowRecord> history = borrowService.getMemberHistory(userId);
        model.addAttribute("history", history);
        return "my-books";   // -> WEB-INF/views/my-books.jsp
    }
}
