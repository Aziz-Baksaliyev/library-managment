package com.library.managment.controller;

import com.library.managment.model.entity.Book;
import com.library.managment.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public List<Book> getAllbooks() {
        return bookService.getAll();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }
    @PostMapping("/books/create")
    public Book createBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping("/books/update/{id}")
    public Book updateAuthor(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/books/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

}
