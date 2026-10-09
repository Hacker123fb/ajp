package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.UserDAO;
import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.User;
import com.library.exception.LibraryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * BorrowService — handles book issue and return logic.
 *
 * issueBook() and returnBook() are @Transactional:
 *   - Both operations (record + quantity update) happen together.
 *   - If one fails, everything rolls back.
 *
 * Fine formula: fine = overdueDays × 10
 */
@Service
public class BorrowService {

    private static final double FINE_PER_DAY = 10.0;

    @Autowired
    private BorrowDAO borrowDAO;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * Issue a book to a member.
     *
     * Steps:
     *  1. Load user and book from DB
     *  2. Check if availableQuantity > 0
     *  3. Create BorrowRecord with status = ISSUED
     *  4. Decrease book.availableQuantity by 1
     *  5. Save both (inside one transaction)
     *
     * @param userId  ID of the member borrowing the book
     * @param bookId  ID of the book being borrowed
     * @param dueDate the due date set by the librarian
     */
    @Transactional
    public void issueBook(int userId, int bookId, Date dueDate) {
        if (dueDate == null) {
            throw new LibraryException("Due date is required.");
        }

        LocalDate dueLocalDate = Instant.ofEpochMilli(dueDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (dueLocalDate.isBefore(LocalDate.now())) {
            throw new LibraryException("Due date cannot be set to a past date.");
        }

        User user = userDAO.findById(userId);
        Book book = bookDAO.findById(bookId);

        if (user == null) {
            throw new LibraryException("Member not found with ID: " + userId);
        }

        if (book == null) {
            throw new LibraryException("Book not found with ID: " + bookId);
        }

        if (book.getAvailableQuantity() <= 0) {
            throw new LibraryException("Book is not available: " + book.getTitle());
        }

        // Create the borrow record
        BorrowRecord record = new BorrowRecord();
        record.setUser(user);
        record.setBook(book);
        record.setIssueDate(new Date());
        record.setDueDate(dueDate);
        record.setStatus("ISSUED");
        record.setFine(0.0);

        // Decrease available quantity
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);

        // Save borrow record and update book (both in one transaction)
        borrowDAO.save(record);
        bookDAO.update(book);
    }

    /**
     * Return a book.
     *
     * Steps:
     *  1. Load BorrowRecord by ID
     *  2. Set return date to today
     *  3. Set status = RETURNED
     *  4. Calculate fine if returned after due date
     *  5. Increase book.availableQuantity by 1
     *  6. Update both (inside one transaction)
     *
     * @param borrowId the ID of the BorrowRecord to return
     */
    @Transactional
    public void returnBook(int borrowId) {
        BorrowRecord record = borrowDAO.findById(borrowId);

        if (record == null) {
            throw new LibraryException("Borrow record not found with ID: " + borrowId);
        }

        if ("RETURNED".equals(record.getStatus())) {
            throw new LibraryException("This book has already been returned.");
        }

        Date today = new Date();
        record.setReturnDate(today);
        record.setStatus("RETURNED");

        // Calculate fine if returned after due date
        double fine = calculateFine(record.getDueDate(), today);
        record.setFine(fine);

        // Increase available quantity
        Book book = record.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);

        // Update borrow record and book (both in one transaction)
        borrowDAO.update(record);
        bookDAO.update(book);
    }

    /**
     * Get all borrow records (for admin/librarian view).
     */
    @Transactional(readOnly = true)
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowDAO.findAll();
    }

    /**
     * Get borrowing history for a specific member.
     * Used on the my-books page.
     */
    @Transactional(readOnly = true)
    public List<BorrowRecord> getMemberHistory(int userId) {
        return borrowDAO.findByUser(userId);
    }

    /**
     * Calculate fine based on overdue days.
     * fine = overdueDays × FINE_PER_DAY (10 per day)
     */
    private double calculateFine(Date dueDate, Date returnDate) {
        if (returnDate.after(dueDate)) {
            long diffMillis = returnDate.getTime() - dueDate.getTime();
            long overdueDays = diffMillis / (1000 * 60 * 60 * 24);
            return overdueDays * FINE_PER_DAY;
        }
        return 0.0;
    }
}
