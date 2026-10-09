package com.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

/**
 * BorrowRecord entity — maps to the "borrow_records" table.
 *
 * Demonstrates Hibernate @ManyToOne relationships:
 *   - Each BorrowRecord belongs to one User  (who borrowed)
 *   - Each BorrowRecord belongs to one Book  (what was borrowed)
 *
 * status values: "ISSUED" | "RETURNED"
 * fine: calculated on return if returned after due date (10 per overdue day)
 */
@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many BorrowRecords -> One User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Many BorrowRecords -> One Book
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Temporal(TemporalType.DATE)
    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private Date returnDate;   // null when book is still ISSUED

    // "ISSUED" or "RETURNED"
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "fine")
    private double fine;       // 0.0 when no overdue fine

    // ===== Constructors =====

    public BorrowRecord() {
    }

    // ===== Getters and Setters =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}
