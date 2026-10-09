package com.library.exception;

/**
 * LibraryException — a simple runtime exception for library business rule violations.
 *
 * Examples:
 *  - Book not available for issue
 *  - Member not found
 *  - Book already returned
 */
public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }
}
