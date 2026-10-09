package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * BookController — handles all book-related operations.
 *
 * URLs:
 *  GET  /books              -> list all books
 *  GET  /books/search       -> search books by title
 *  GET  /books/add          -> show add book form
 *  POST /books/add          -> save new book
 *  GET  /books/edit/{id}    -> show edit book form
 *  POST /books/edit         -> update book
 *  GET  /books/delete/{id}  -> delete book
 */
@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * List all books.
     */
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";   // -> WEB-INF/views/books.jsp
    }

    /**
     * Search books by title keyword.
     */
    @GetMapping("/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model) {
        List<Book> books = bookService.searchBooks(keyword);
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "books";   // reuse books.jsp
    }

    /**
     * Show the Add Book form.
     * Puts an empty Book object in the model for the form to bind to.
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("formAction", "Add Book");
        return "book-form";   // -> WEB-INF/views/book-form.jsp
    }

    /**
     * Process the Add Book form submission.
     */
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    /**
     * Show the Edit Book form.
     * Loads the existing book data into the form.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("formAction", "Edit Book");
        return "book-form";   // reuse book-form.jsp
    }

    /**
     * Process the Edit Book form submission.
     */
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute("book") Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }

    /**
     * Delete a book by ID.
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
